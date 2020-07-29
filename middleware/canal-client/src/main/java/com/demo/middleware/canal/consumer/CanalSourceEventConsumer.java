package com.demo.middleware.canal.consumer;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.demo.middleware.canal.config.CanalConfigProperties;
import com.demo.middleware.canal.config.DestinationConfig;
import com.demo.middleware.canal.connection.LongCanalConnection;
import com.demo.middleware.canal.enums.OperateType;
import com.demo.middleware.canal.message.StandardMessage;
import com.demo.middleware.canal.message.StandardRowData;
import com.demo.middleware.canal.utils.TypeConverter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author youyu
 */
@Component
public class CanalSourceEventConsumer extends AbstractSourceEventConsumer<List<CanalEntry.Entry>> implements SmartInitializingSingleton {

    @Autowired
    private CanalConfigProperties properties;

    @Autowired
    private CanalConnector canalConnector;

    @Autowired
    private Runnable connection;

    @Getter
    private volatile boolean running;

    @Value("${adapter.event.beforeColumns:false}")
    private boolean beforeColumns;

    @Override
    protected List<StandardMessage> parse(String database, List<CanalEntry.Entry> entries) {
        return new CanalEntryConverter(database,entries,beforeColumns).convert();
    }

    @Override
    protected void records(String database, List<CanalEntry.Entry> entries) {

    }

    @Override
    public void afterSingletonsInstantiated() {
        running = true;

        Map.Entry<String, DestinationConfig> entry = Iterables.get(properties.getDestination().entrySet(), 0);
        String destination = entry.getKey();
        DestinationConfig destinationConfig = entry.getValue();

        String addressList = properties.getNodes().getAddressList();

        String address = addressList.split(",")[0];
        String[] hp = address.split(":");

        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(hp[0], Integer.parseInt(hp[1])), destination, properties.getNodes().getUsername(), properties.getNodes().getPassword());

        LongCanalConnection longCanalConnection = new LongCanalConnection(destination, connector, destinationConfig, this);

        new Thread(longCanalConnection,"canal-event-dest-"+destination).start();
    }

    @Slf4j
    static class CanalEntryConverter{
        private List<CanalEntry.Entry> entries;
        private String destination;
        private boolean beforeColumns;

        public CanalEntryConverter(String destination, List<CanalEntry.Entry> entries, boolean beforeColumns) {
            if (CollectionUtils.isEmpty(entries)){
                throw new RuntimeException("entries must not be empty!");
            }
            this.destination = destination;
            this.entries = entries;
            this.beforeColumns = beforeColumns;
        }

        public void logInfo(){
            CanalEntry.Header header = entries.get(entries.size() - 1).getHeader();
            log.info("Message: destination={},size={},logFile={},offset={}",destination,entries.size()
                    ,header.getLogfileName(),header.getLogfileOffset());
        }

        public List<StandardMessage> convert(){
            logInfo();
            //filter null object after converted
            return entries.stream().map(this::convert).filter(Objects::nonNull).collect(Collectors.toList());
        }

        public StandardMessage convert(CanalEntry.Entry entry){
            try {
                if (entry.getEntryType() != CanalEntry.EntryType.ROWDATA){
                    return null;
                }
                CanalEntry.Header header = entry.getHeader();

                StandardMessage standardMessage = new StandardMessage();
                standardMessage.setDomainName(header.getTableName());

                switch (header.getEventType()){
                    case INSERT:
                        standardMessage.setOperateType(OperateType.ADD);
                        standardMessage.setData(parseWhenInsert(parse(entry)));
                        return standardMessage;
                    case UPDATE:
                        standardMessage.setOperateType(OperateType.UPDATE);
                        standardMessage.setData(parseWhenUpdate(parse(entry)));
                        return standardMessage;
                    case DELETE:
                        standardMessage.setOperateType(OperateType.DELETE);
                        standardMessage.setData(parseWhenDelete(parse(entry)));
                        return standardMessage;
                    default:
                        return null;
                }
            }catch (Exception e){
                //keep on converting no matter what a exception
                log.error("there is a exception while converting CanalEntry.Entry:{}",entry);
                return null;
            }
        }


        public CanalEntry.RowChange parse(CanalEntry.Entry entry){
            CanalEntry.RowChange rowChange;
            try {
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                if (rowChange.getIsDdl()){
                    return null;
                }
            } catch (InvalidProtocolBufferException e) {
                log.error(e.getMessage(),e);
                return null;
            }
            if (rowChange.getRowDatasCount()<=0){
                return null;
            }
            return rowChange;
        }

        public List<StandardRowData> parse(CanalEntry.RowChange rowChange, Function<CanalEntry.RowData,StandardRowData> messageExtractor){
            if (rowChange == null){
                return null;
            }
            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
            if (CollectionUtils.isEmpty(rowDatasList)){
                return Collections.EMPTY_LIST;
            }
            return rowDatasList.stream().map(messageExtractor).collect(Collectors.toList());
        }

        public List<StandardRowData> parseWhenInsert(CanalEntry.RowChange rowChange){
            return parse(rowChange,rowData -> doParse(rowData.getAfterColumnsList()));
        }

        private List<StandardRowData> parseWhenUpdate(CanalEntry.RowChange rowChange) {
            return parse(rowChange,rowData -> {
                StandardRowData standardRowData = doParse(rowData.getAfterColumnsList());
                if (beforeColumns){
                    standardRowData.setPreColumns(columns2map(rowData.getBeforeColumnsList(),null));
                }
                return standardRowData;
            });
        }

        private List<StandardRowData> parseWhenDelete(CanalEntry.RowChange rowChange) {
            return parse(rowChange,rowData -> doParse(rowData.getBeforeColumnsList()));
        }


        public StandardRowData doParse(List<CanalEntry.Column> columns){
            StandardRowData rowData = new StandardRowData();
            rowData.setColumns(columns2map(columns, rowData));
            return rowData;
        }

        private HashMap<String, Object> columns2map(List<CanalEntry.Column> columns, StandardRowData standardRowData) {
            return columns.stream().map(column2mapEntry(standardRowData)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, HashMap::new));
        }

        private Function<CanalEntry.Column, Map.Entry<String, ?>> column2mapEntry(StandardRowData standardRowData) {
            return column -> {
                if (standardRowData != null && column.getIsKey()) {
                    try {
                        standardRowData.setId(Long.valueOf(column.getValue()));
                    } catch (NumberFormatException e) {
                        log.error("failed to parse table primary key to java.lang.Long,column={},value={}", column.getName(), column.getValue());
                    }
                    return Maps.immutableEntry(column.getName(), standardRowData.getId());
                }
                return Maps.immutableEntry(column.getName(), TypeConverter.convertSqlTypeValue(column.getSqlType(), column.getValue()));
            };
        }

    }

    @PreDestroy
    public void stop() {
        running = false;
    }
}

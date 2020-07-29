package com.demo.middleware.canal.connection;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.exception.CanalClientException;
import com.demo.middleware.canal.config.CanalConfigProperties;
import com.demo.middleware.canal.config.DestinationConfig;
import com.demo.middleware.canal.consumer.CanalSourceEventConsumer;
import com.google.common.collect.Iterables;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.utils.ThreadUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Long connection to canal server for acquiring binlog
 * @author youyu
 */
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Component
public class LongCanalConnection implements Runnable, SmartInitializingSingleton {

    @Autowired
    private CanalConfigProperties properties;

    private String destination;

    @Setter
    @Getter
    private CanalConnector connector;

    private DestinationConfig config;

    @Autowired
    private CanalSourceEventConsumer canalSourceEventConsumer;

    private volatile boolean running;

    @Override
    public void run() {
        while (canalSourceEventConsumer.isRunning()){
            try {
                connector.connect();
                connector.subscribe(config.getFilterRegex());
                connector.rollback();
                log.warn("It is success to connect Canal server.destination={},filterRegex={}",destination,config.getFilterRegex());
                pullMessage();
            }catch (Exception e){
                log.error("There is exception when connect Canal server.destination={},cause:",destination,e);
                try {
                    Thread.sleep(config.getBlockWaitMillis());
                } catch (InterruptedException interruptedException) {
                    log.error(interruptedException.getMessage(),interruptedException);
                    break;
                }
                try {
                    disconnect();
                }catch (CanalClientException ce){
                    log.warn(ce.getMessage(),ce);
                }
            }
        }
        disconnect();
    }

    private void disconnect() throws CanalClientException{
        if (connector.checkValid()) {
            connector.stopRunning();
        }
        connector.disconnect();
    }

    private void pullMessage() {
        while (canalSourceEventConsumer.isRunning()){
            Message message = (config.getBlockWaitMillis() <= 0) ?
                    connector.getWithoutAck(config.getPullBatchSize()) :
                    connector.getWithoutAck(config.getPullBatchSize(), config.getBlockWaitMillis(), TimeUnit.MILLISECONDS);
            long batchId = message.getId();
            int size = message.getEntries().size();
            if (batchId == -1 || size<=0){
                try {
                    Thread.sleep(config.getIdleSpinMillis());
                } catch (InterruptedException e) {
                    log.warn(e.getMessage(),e);
                    //if thread has been interrupted,skip loop
                    break;
                }
            }else {
                try {
                    canalSourceEventConsumer.process(destination,message.getEntries());
                    connector.ack(batchId);
                }catch (Exception e){
                    log.error(e.getMessage(),e);
                    //if there is exception when process message,rollback it and keep on pulling message
                    connector.rollback(batchId);
                }
            }
        }
    }

    @Override
    public void afterSingletonsInstantiated() {
        running = true;

        Map.Entry<String, DestinationConfig> entry = Iterables.get(properties.getDestination().entrySet(), 0);

        destination = entry.getKey();
        config = entry.getValue();

        String addressList = properties.getNodes().getAddressList();

        String address = addressList.split(",")[0];
        String[] hp = address.split(":");

        connector = CanalConnectors.newSingleConnector(new InetSocketAddress(hp[0], Integer.parseInt(hp[1])), destination, properties.getNodes().getUsername(), properties.getNodes().getPassword());
        new Thread(this,"canal-event-dest-"+destination).start();
    }
}

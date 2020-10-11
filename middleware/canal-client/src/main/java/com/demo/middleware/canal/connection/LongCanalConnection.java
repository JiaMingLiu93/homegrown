package com.demo.middleware.canal.connection;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.exception.CanalClientException;
import com.demo.middleware.canal.consumer.CanalSourceEventConsumer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Long connection to canal server for acquiring binlog
 * LongCanalConnection also could be abstract to LongConnection for kinds of implements to connect source,
 * which is convenient to scale,for example,connecting to broker of rocket mq.
 * @author youyu
 */
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Component
public class LongCanalConnection implements Runnable, SmartInitializingSingleton {

    @Autowired
    private ConfigurationReadableCanalConnector connector;

    @Autowired
    private CanalSourceEventConnector canalSourceEventConnector;

    @Autowired
    private CanalSourceEventConsumer canalSourceEventConsumer;


    @Override
    public void run() {
        while (canalSourceEventConnector.isRunning()){
            canalSourceEventConnector.connect();
            List<CanalEntry.Entry> entries = canalSourceEventConnector.acquire();
            canalSourceEventConsumer.process(canalSourceEventConnector.getSource(),entries);
        }
        canalSourceEventConnector.disConnect();



        ////////////////////////////////
        while (connector.isRunning()){
            try {
                connector.connect();
                connector.subscribe(connector.getDestinationConfig().getFilterRegex());
                connector.rollback();
                log.warn("It is success to connect Canal server.destination={},filterRegex={}",connector.getDestination(),connector.getDestinationConfig().getFilterRegex());
                pullMessage();
            }catch (Exception e){
                log.error("There is exception when connect Canal server.destination={},cause:",connector.getDestination(),e);
                try {
                    Thread.sleep(connector.getDestinationConfig().getBlockWaitMillis());
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
        while (connector.isRunning()){
            Message message = (connector.getDestinationConfig().getBlockWaitMillis() <= 0) ?
                    connector.getWithoutAck(connector.getDestinationConfig().getPullBatchSize()) :
                    connector.getWithoutAck(connector.getDestinationConfig().getPullBatchSize(), connector.getDestinationConfig().getBlockWaitMillis(), TimeUnit.MILLISECONDS);
            long batchId = message.getId();
            int size = message.getEntries().size();
            if (batchId == -1 || size<=0){
                try {
                    Thread.sleep(connector.getDestinationConfig().getIdleSpinMillis());
                } catch (InterruptedException e) {
                    log.warn(e.getMessage(),e);
                    //if thread has been interrupted,skip loop
                    break;
                }
            }else {
                try {
                    canalSourceEventConsumer.process(connector.getDestination(),message.getEntries());
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
        new Thread(this,"canal-event-dest-"+connector).start();
    }
}

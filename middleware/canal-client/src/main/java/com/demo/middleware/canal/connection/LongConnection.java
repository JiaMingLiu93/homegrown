package com.demo.middleware.canal.connection;

import com.demo.middleware.canal.connector.SourceEventConnector;
import com.demo.middleware.canal.consumer.SourceEventConsumer;

/**
 * @author youyu
 */
public class LongConnection implements Runnable{
    private SourceEventConnector connector;
    private SourceEventConsumer consumer;

    public LongConnection(SourceEventConnector connector, SourceEventConsumer consumer) {
        this.consumer = consumer;
        this.connector = connector;
    }

    @Override
    public void run() {
        while (connector.isRunning()){
            connector.connect();
            consumer.process(connector.getSource(),connector.acquire());
        }
        connector.disConnect();
    }
}

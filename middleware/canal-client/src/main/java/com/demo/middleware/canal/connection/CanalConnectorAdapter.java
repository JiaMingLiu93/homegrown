package com.demo.middleware.canal.connection;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.exception.CanalClientException;
import com.demo.middleware.canal.config.CanalConfigProperties;
import com.demo.middleware.canal.config.DestinationConfig;
import com.google.common.collect.Iterables;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Adapter for {@link CanalConnector}
 * @author youyu
 */
//@Component
public class CanalConnectorAdapter implements ConfigurationReadableCanalConnector {

    private CanalConnector connector;

    private volatile boolean running;

    @Autowired
    private CanalConfigProperties properties;

    @Getter
    private String destination;

    @Getter
    private DestinationConfig destinationConfig;

    @Override
    public void connect() throws CanalClientException {
        connector.connect();
    }

    @Override
    public void disconnect() throws CanalClientException {
        connector.disconnect();
    }

    @Override
    public boolean checkValid() throws CanalClientException {
        return connector.checkValid();
    }

    @Override
    public void subscribe(String filter) throws CanalClientException {
        connector.subscribe(filter);
    }

    @Override
    public void subscribe() throws CanalClientException {
        connector.subscribe();
    }

    @Override
    public void unsubscribe() throws CanalClientException {
        connector.unsubscribe();
    }

    @Override
    public Message get(int batchSize) throws CanalClientException {
        return connector.get(batchSize);
    }

    @Override
    public Message get(int batchSize, Long timeout, TimeUnit unit) throws CanalClientException {
        return connector.get(batchSize,timeout,unit);
    }

    @Override
    public Message getWithoutAck(int batchSize) throws CanalClientException {
        return connector.getWithoutAck(batchSize);
    }

    @Override
    public Message getWithoutAck(int batchSize, Long timeout, TimeUnit unit) throws CanalClientException {
        return connector.getWithoutAck(batchSize,timeout,unit);
    }

    @Override
    public void ack(long batchId) throws CanalClientException {
        connector.ack(batchId);
    }

    @Override
    public void rollback(long batchId) throws CanalClientException {
        connector.rollback(batchId);
    }

    @Override
    public void rollback() throws CanalClientException {
        connector.rollback();
    }

    @Override
    public void stopRunning() throws CanalClientException {
        connector.stopRunning();
    }

    @Override
    public Boolean isRunning(){
        return running;
    }

    @PostConstruct
    public void init() {
        running = true;

        Map.Entry<String, DestinationConfig> entry = Iterables.get(properties.getDestination().entrySet(), 0);

        destination = entry.getKey();
        destinationConfig = entry.getValue();

        String addressList = properties.getNodes().getAddressList();

        String address = addressList.split(",")[0];
        String[] hp = address.split(":");

        connector = CanalConnectors.newSingleConnector(new InetSocketAddress(hp[0], Integer.parseInt(hp[1])), destination, properties.getNodes().getUsername(), properties.getNodes().getPassword());
    }
}

package com.demo.middleware.canal.connection;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.exception.CanalClientException;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author youyu
 */
public class CompositeConnector implements CanalConnector {
    List<CanalConnector> canalConnectors;

    @Override
    public void connect() throws CanalClientException {
        canalConnectors.forEach(CanalConnector::connect);
    }

    @Override
    public void disconnect() throws CanalClientException {

    }

    @Override
    public boolean checkValid() throws CanalClientException {
        return false;
    }

    @Override
    public void subscribe(String filter) throws CanalClientException {

    }

    @Override
    public void subscribe() throws CanalClientException {

    }

    @Override
    public void unsubscribe() throws CanalClientException {

    }

    @Override
    public Message get(int batchSize) throws CanalClientException {
        return null;
    }

    @Override
    public Message get(int batchSize, Long timeout, TimeUnit unit) throws CanalClientException {
        return null;
    }

    @Override
    public Message getWithoutAck(int batchSize) throws CanalClientException {
        return null;
    }

    @Override
    public Message getWithoutAck(int batchSize, Long timeout, TimeUnit unit) throws CanalClientException {
        return null;
    }

    @Override
    public void ack(long batchId) throws CanalClientException {

    }

    @Override
    public void rollback(long batchId) throws CanalClientException {

    }

    @Override
    public void rollback() throws CanalClientException {

    }

    @Override
    public void stopRunning() throws CanalClientException {

    }
}

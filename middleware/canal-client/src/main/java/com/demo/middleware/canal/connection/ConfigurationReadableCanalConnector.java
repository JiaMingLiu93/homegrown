package com.demo.middleware.canal.connection;

import com.alibaba.otter.canal.client.CanalConnector;
import com.demo.middleware.canal.config.DestinationConfig;

public interface ConfigurationReadableCanalConnector extends CanalConnector {
    String getDestination();
    DestinationConfig getDestinationConfig();
    Boolean isRunning();
    public void init();
}

package com.demo.middleware.canal.connection;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author youyu
 */
@Component
public class ConnectionPoolManager implements SmartInitializingSingleton {
    @Autowired
    private List<ConfigurationReadableCanalConnector> connectors;

    @Override
    public void afterSingletonsInstantiated() {

    }
}

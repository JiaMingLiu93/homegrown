package com.demo.middleware.canal.connection;

import com.demo.middleware.canal.config.CanalConfigProperties;
import com.demo.middleware.canal.connector.SourceEventConnector;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * @author youyu
 */
@Slf4j
public class CanalSourceEventConnectorManager extends AbstractSourceEventConnectorManager{

    private final CanalConfigProperties properties;

    public CanalSourceEventConnectorManager(CanalConfigProperties properties) {
        this.properties = properties;
    }

    @Override
    protected Collection<? extends SourceEventConnector> loadConnectors() {
        return null;
    }
}

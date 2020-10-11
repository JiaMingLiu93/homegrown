package com.demo.middleware.canal.connection;

import com.demo.middleware.canal.connector.SourceEventConnector;
import org.springframework.lang.Nullable;

import java.util.Collection;

/**
 * @author youyu
 * @date 2020/8/26 7:19 PM
 */
public interface SourceEventConnectorManager {
    @Nullable
    SourceEventConnector getConnector(String name);
    Collection<String> getConnectorNames();
}

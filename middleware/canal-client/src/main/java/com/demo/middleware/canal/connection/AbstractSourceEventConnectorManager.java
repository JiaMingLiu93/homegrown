package com.demo.middleware.canal.connection;

import com.demo.middleware.canal.connector.SourceEventConnector;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author youyu
 */
public abstract class AbstractSourceEventConnectorManager implements SourceEventConnectorManager, InitializingBean {

    private final ConcurrentHashMap<String,SourceEventConnector> connectors = new ConcurrentHashMap<>(16);;
    private volatile Set<String> connectorNames = Collections.emptySet();

    @Override
    @Nullable
    public SourceEventConnector getConnector(String name) {
        SourceEventConnector connector = connectors.get(name);
        if (connector != null){
            return connector;
        }else {

        }
        return null;
    }

    @Override
    public Collection<String> getConnectorNames() {
        return this.connectorNames;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initializeConnectors();
    }

    /**
     * Initialize the static configuration of connectors.
     * Triggered on startup through {@link #afterPropertiesSet()};
     * can also be called to re-initialize at runtime.
     * @see #loadConnectors()
     */
    public void initializeConnectors() {
        Collection<? extends SourceEventConnector> connectors = loadConnectors();

        synchronized (this.connectors){
            this.connectorNames = Collections.emptySet();
            this.connectors.clear();

            Set<String> connectorNames = new LinkedHashSet<>(connectors.size());
            for (SourceEventConnector connector:connectors){
                String source = connector.getSource();
                this.connectors.put(source,decorate(connector));
                connectorNames.add(source);
            }

            this.connectorNames = Collections.unmodifiableSet(connectorNames);
        }
    }

    protected SourceEventConnector decorate(SourceEventConnector connector) {
        return connector;
    }

    protected abstract Collection<? extends SourceEventConnector> loadConnectors();
}

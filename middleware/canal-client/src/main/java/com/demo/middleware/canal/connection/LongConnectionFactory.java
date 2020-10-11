package com.demo.middleware.canal.connection;

import com.demo.middleware.canal.connector.SourceEventConnector;
import com.demo.middleware.canal.consumer.SourceEventConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author youyu
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LongConnectionFactory implements SmartInitializingSingleton {

    private final SourceEventConnectorManager sourceEventConnectorManager;
    private final SourceEventConsumer sourceEventConsumer;

    @Override
    public void afterSingletonsInstantiated() {
        Collection<String> connectorNames = sourceEventConnectorManager.getConnectorNames();
        log.info("available connectors are{}",connectorNames);
        connectorNames.forEach(name->{
            SourceEventConnector connector = sourceEventConnectorManager.getConnector(name);
            if (connector != null){
                LongConnection longConnection = new LongConnection(connector, sourceEventConsumer);
                new Thread(longConnection,name+"-event-dest-"+connector).start();
            }
        });
    }
}

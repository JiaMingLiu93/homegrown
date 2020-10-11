package com.demo.middleware.canal;

import com.demo.middleware.canal.connection.*;
import com.demo.middleware.canal.consumer.SourceEventConsumer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author youyu
 */
@Configuration
public class ConnectorConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ConfigurationReadableCanalConnector configurationReadableCanalConnector(){
        return new CanalConnectorAdapter();
    }

    @Bean
    public SourceEventConnectorManager sourceEventConnectorManager(){
        return null;
    }
}

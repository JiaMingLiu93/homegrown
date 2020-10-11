package com.demo.middleware.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.demo.middleware.canal.connection.SourceEventConnectorManager;
import com.demo.middleware.canal.consumer.SourceEventConsumer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author youyu
 */
@Configuration
@ConditionalOnClass(CanalConnector.class)
public class CanalConnectorConfiguration {
    @Bean
    public SourceEventConnectorManager sourceEventConnectorManager(){
        return null;
    }
    @Bean
    public SourceEventConsumer sourceEventConsumer(){
        return null;
    }
}

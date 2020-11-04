package com.demo.middleware.canal.consumer.config;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.demo.middleware.canal.config.DestinationConfig;
import com.demo.middleware.canal.connection.ConfigurationReadableCanalConnector;
import org.assertj.core.util.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * @author youyu
 */
@Configuration
public class TestConfiguration {

    @Bean
    public ConfigurationReadableCanalConnector configurationReadableCanalConnector(){
        Message message = new Message(1L);
        ArrayList<CanalEntry.Entry> entries = Lists.newArrayList(CanalEntry.Entry.getDefaultInstance());
        message.setEntries(entries);

        ConfigurationReadableCanalConnector connector = mock(ConfigurationReadableCanalConnector.class);

        given(connector.getWithoutAck(anyInt(),anyLong(), (TimeUnit) any())).willReturn(message);
        given(connector.isRunning()).willReturn(true);
        DestinationConfig destinationConfig = new DestinationConfig();
        given(connector.getDestinationConfig()).willReturn(destinationConfig);
        return connector;
    }
}


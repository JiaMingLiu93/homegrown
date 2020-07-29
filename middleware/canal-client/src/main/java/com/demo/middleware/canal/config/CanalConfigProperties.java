package com.demo.middleware.canal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author youyu
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "adapter.event.canal.server")
public class CanalConfigProperties {
    @NestedConfigurationProperty
    private ConnectionConfig nodes;
    @NestedConfigurationProperty
    private HashMap<String, DestinationConfig> destination;
}

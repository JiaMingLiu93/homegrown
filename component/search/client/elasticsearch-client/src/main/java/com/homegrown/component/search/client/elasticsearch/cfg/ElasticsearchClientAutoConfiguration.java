package com.homegrown.component.search.client.elasticsearch.cfg;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

/**
 * @author youyu
 */
@ComponentScan("com.homegrown.component.search.client.elasticsearch")
@EnableConfigurationProperties({HttpSearchProperties.class})
public class ElasticsearchClientAutoConfiguration {
}

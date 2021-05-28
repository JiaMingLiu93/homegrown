package com.homegrown.component.search.client.elasticsearch.cfg;

import lombok.Data;

/**
 * @author youyu
 */
@Data
public class HttpConnectionPoolProperties {
    /**
     * Max idle
     */
    private int maxIdle = 5;
    /**
     * Keep alive milliseconds
     */
    private long keepAlive = 5 * 60_000;
    /**
     * Call timeout milliseconds, default null
     */
    private Long callTimeout;
    /**
     * Call timeout milliseconds, default 10_000
     */
    private Long readTimeout;
    /**
     * Call timeout milliseconds, default 10_000
     */
    private Long writeTimeout;
    /**
     * Call timeout milliseconds, default 10_000
     */
    private Long connectTimeout;
}

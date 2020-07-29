package com.demo.middleware.canal.config;

import lombok.Data;

/**
 * @author youyu
 * @date 2020/7/1 11:46 AM
 */
@Data
public class DestinationConfig {
    /**
     * Canal binlog过滤正则表达式
     */
    private String filterRegex = "";
    /**
     * Canal 连接失败时尝试重连时间间隔
     */
    private long retryConnectSleepMillis = 60000;

    /**
     * Canal 从事件队列单次pull时最大消息数量
     */
    private int pullBatchSize = 100;

    /**
     * Canal 单次pull时在服务端夯住等待的时间
     */
    private long blockWaitMillis = 500;

    /**
     * Canal 队列空闲时本地自旋等待的超时时间
     */
    private long idleSpinMillis = 500;
}

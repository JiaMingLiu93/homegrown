package com.demo.middleware.canal.consumer;

import java.util.concurrent.Executor;

/**
 * @author youyu
 */
public interface SourceEventConsumer {
    /**
     * set customise and appropriate executor for parallel processing event.
     * @param executor executor
     */
    void setExecutor(Executor executor);

    /**
     * process event about domain
     * @param domain the range of event
     * @param event event from the domain
     */
    void process(String domain,Object event);
}

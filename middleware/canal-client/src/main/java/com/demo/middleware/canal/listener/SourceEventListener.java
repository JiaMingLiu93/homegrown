package com.demo.middleware.canal.listener;

import com.demo.middleware.canal.message.StandardMessage;

/**
 * @author youyu
 * @date 2020/7/7 8:55 PM
 */
public interface SourceEventListener {
    /**
     *
     * @param standardMessage standard message
     * @throws Exception if the message handling fails
     */
    default void onStandardMessage(StandardMessage standardMessage) throws Exception{}
    default void onStandardEvent(){}
}

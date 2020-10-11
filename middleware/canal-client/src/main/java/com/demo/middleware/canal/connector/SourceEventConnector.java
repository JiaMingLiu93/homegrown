package com.demo.middleware.canal.connector;

/**
 * @author youyu
 * @date 2020/8/24 7:56 PM
 */
public interface SourceEventConnector {
    Boolean isRunning();
    void connect();
    Object acquire();
    void disConnect();
    String getSource();
}

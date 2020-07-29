package com.demo.middleware.canal.config;

import lombok.Data;

/**
 * @author youyu
 */
@Data
public class ConnectionConfig {
    private String addressList;
    private String username = "";
    private String password = "";
}

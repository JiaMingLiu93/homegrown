package com.demo.dubbo.spi;

import com.demo.server.spi.DataBaseDriver;

/**
 * @author jam
 * @description
 * @create 2018-12-11
 **/
public class MysqlDriver implements DataBaseDriver {
    @Override
    public String connect(String host) {
        return host;
    }
}

package com.demo.dubbo.spi;


import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Protocol;
import com.demo.server.spi.DataBaseDriver;

import java.util.ServiceLoader;

/**
 * @author jam
 * @description
 * @create 2018-12-11
 **/
public class SPITest {
    public static void main(String[] args) {
        ServiceLoader<DataBaseDriver> drivers = ServiceLoader.load(DataBaseDriver.class);
        for (DataBaseDriver driver:drivers){
            System.out.println(driver.connect("driver"));
        }

        int defaultPort = ExtensionLoader.getExtensionLoader(Protocol.class).getDefaultExtension().getDefaultPort();
        System.out.println(defaultPort);

        Protocol adaptiveExtension = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
    }
}

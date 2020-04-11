package com.homegrown.basic.log.log4j;

import com.homegrown.basic.log.log4j.debug.LogDebugDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author youyu
 * @date 2020/1/16 3:14 PM
 */
public class DemoApplication {
    private static Logger logger = LoggerFactory.getLogger(DemoApplication.class);
    public static void main(String[] args) {
        LogDebugDemo logDebugDemo = new LogDebugDemo();
        logger.info("DemoApplication class print info.");
        logDebugDemo.print(1);
    }
}

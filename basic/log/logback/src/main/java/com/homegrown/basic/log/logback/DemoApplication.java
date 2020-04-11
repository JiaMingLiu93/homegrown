package com.homegrown.basic.log.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author youyu
 * @date 2020/1/16 3:36 PM
 */
public class DemoApplication {
    private static Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        logger.debug("DemoApplication of logback print info");
    }
}

package com.homegrown.basic.log.log4j.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author youyu
 * @date 2020/1/16 3:09 PM
 */
public class LogDebugDemo {
    private Logger logger = LoggerFactory.getLogger(LogDebugDemo.class);
    public void print(int i){
        logger.debug("logDebugDemo class print debug info,param={}.",i);
    }
}

package com.demo.springbootaop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jam
 * @description
 * @create 2018-12-01
 **/
public class AdderAfterReturnAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public void afterReturn(Object returnValue) throws Throwable {
        logger.info("value return was {}",  returnValue);
    }
    public void beforeCall(Object a,Object b){
        logger.info("before call do something.");
    }
}

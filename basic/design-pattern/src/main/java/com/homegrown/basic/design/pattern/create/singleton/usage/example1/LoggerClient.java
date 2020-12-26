package com.homegrown.basic.design.pattern.create.singleton.usage.example1;

/**
 * @author youyu
 */
public class LoggerClient {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        logger.log("i am you.");
    }
}

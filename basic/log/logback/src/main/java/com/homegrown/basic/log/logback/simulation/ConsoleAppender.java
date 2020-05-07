package com.homegrown.basic.log.logback.simulation;

import ch.qos.logback.core.OutputStreamAppender;

/**
 * @author youyu
 * @date 2020/4/14 5:15 PM
 */
public class ConsoleAppender<E> extends OutputStreamAppender<E> {
    @Override
    public void start() {
        System.out.println("================");
        super.start();
    }
}

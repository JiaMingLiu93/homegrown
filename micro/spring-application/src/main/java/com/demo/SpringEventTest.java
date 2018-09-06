package com.demo;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Objects;

/**
 * @author jam
 * @description
 * @create 2018-08-28
 **/
public class SpringEventTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册一个 Configuration Class = SpringAnnotationDemo
        context.register(SpringAnnotationDemo.class);
        context.addApplicationListener(event->{
            System.err.println(event.getClass());
        });
        // 上下文启动
        context.refresh();

        MyEvent myEvent = new MyEvent("test event.");

        context.publishEvent(myEvent);
    }

    public static class MyEvent extends ApplicationEvent {
        private String info;

        /**
         * Create a new ApplicationEvent.
         *
         * @param source the object on which the event initially occurred (never {@code null})
         */
        public MyEvent(Object source) {
            super(source);
        }
    }
}

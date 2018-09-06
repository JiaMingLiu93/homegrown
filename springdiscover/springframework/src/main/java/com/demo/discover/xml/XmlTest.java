package com.demo.discover.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jam
 * @description
 * @create 2018-09-01
 **/
public class XmlTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        XmlConfigBean xmlConfigBean = (XmlConfigBean) context.getBean("xmlConfigBean");
        System.out.println(xmlConfigBean.getName());
    }
}

package com.demo.discover.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author jam
 * @description
 * @create 2018-11-21
 **/
public class AnnotationBeanTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AnnotationConfigBean.class);
        AnnotationBean annotationBean = (AnnotationBean) annotationConfigApplicationContext.getBean("annotationBean");
        System.out.println(annotationBean.getId()+" "+annotationBean.getName());
    }
}

package com.demo.discover.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jam
 * @description
 * @create 2018-11-21
 **/
@Configuration
public class AnnotationConfigBean {

    @Bean
    public AnnotationBean annotationBean(){
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setId(1L);
        annotationBean.setName("annotationBean");
        return annotationBean;
    }
}

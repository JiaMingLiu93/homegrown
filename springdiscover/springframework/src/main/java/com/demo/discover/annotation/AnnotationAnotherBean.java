package com.demo.discover.annotation;

/**
 * @author jam
 * @description
 * @create 2018-11-21
 **/
public class AnnotationAnotherBean {
    private String name;
    private Long id;
    private AnnotationBean annotationBean;

    public AnnotationBean getAnnotationBean() {
        return annotationBean;
    }

    public void setAnnotationBean(AnnotationBean annotationBean) {
        this.annotationBean = annotationBean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

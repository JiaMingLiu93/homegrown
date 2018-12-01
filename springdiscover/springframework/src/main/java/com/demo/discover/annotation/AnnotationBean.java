package com.demo.discover.annotation;

/**
 * @author jam
 * @description
 * @create 2018-11-21
 **/
public class AnnotationBean {
    private String name;
    private Long id;
    private AnnotationAnotherBean annotationAnotherBean;

    public AnnotationAnotherBean getAnnotationAnotherBean() {
        return annotationAnotherBean;
    }

    public void setAnnotationAnotherBean(AnnotationAnotherBean annotationAnotherBean) {
        this.annotationAnotherBean = annotationAnotherBean;
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

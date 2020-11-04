package com.demo.test.mapstruct.entity;

import lombok.Data;

/**
 * @author jam
 * @description
 * @create 2018-09-07
 **/
@Data
public class Ming {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

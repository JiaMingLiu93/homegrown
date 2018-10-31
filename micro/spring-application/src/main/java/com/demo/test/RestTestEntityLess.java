package com.demo.test;

/**
 * @author jam
 * @description
 * @create 2018-10-22
 **/
public class RestTestEntityLess {
    private String name;

    private String hobby;

    private InnerOne1 one;

    public InnerOne1 getOne() {
        return one;
    }

    public void setOne(InnerOne1 one) {
        this.one = one;
    }

    public RestTestEntityLess(String name, String hobby) {
        this.name = name;
        this.hobby = hobby;
    }

    public RestTestEntityLess() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return name+","+hobby;
    }
}

class InnerOne1{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

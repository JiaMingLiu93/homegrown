package com.demo.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jam
 * @description
 * @create 2018-10-22
 **/
@RestController
public class RestTestController {
    @GetMapping("/get")
    public RestTestEntity get(String name){
        System.out.println(name);
        RestTestEntity restTestEntity = new RestTestEntity();
        restTestEntity.setHobby("haha");
        restTestEntity.setName("ha");

        InnerOne one = new InnerOne();
        one.setName("one");

        InnerTwo two = new InnerTwo();
        two.setName("two");

        restTestEntity.setTwo(two);
        restTestEntity.setOne(one);

        return restTestEntity;
    }


}


class RestTestEntity {
    private String name;
    private String sex;
    private String hobby;
    private InnerOne one;
    private InnerTwo two;

    public RestTestEntity(String name, String sex, String hobby) {
        this.name = name;
        this.sex = sex;
        this.hobby = hobby;
    }

    public RestTestEntity() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public InnerOne getOne() {
        return one;
    }

    public void setOne(InnerOne one) {
        this.one = one;
    }

    public InnerTwo getTwo() {
        return two;
    }

    public void setTwo(InnerTwo two) {
        this.two = two;
    }
}

class InnerOne{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class InnerTwo{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
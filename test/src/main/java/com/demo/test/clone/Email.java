package com.demo.test.clone;

import java.io.Serializable;

/**
 * @author jam
 * @description
 * @create 2018-12-03
 **/
public class Email implements Serializable {
    private String address;

    public Email(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

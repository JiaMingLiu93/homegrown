package com.demo.test;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * @author jam
 * @description
 * @create 2018-10-22
 **/
public class RestTest {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","liu");
        RestTestEntityLess object = restTemplate.getForObject("http://localhost:8080/get?name={name}", RestTestEntityLess.class,map);
        System.out.println(object);
    }
}

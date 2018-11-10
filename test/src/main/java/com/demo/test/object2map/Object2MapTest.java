package com.demo.test.object2map;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * @author jam
 * @description
 * @create 2018-10-23
 **/
public class Object2MapTest {
    public static void main(String[] args) {
        Object2MapEntity entity = new Object2MapEntity("liu", 18L);
        System.out.println(getMap(entity));
    }

    private static <T> Map getMap(T param){
        ObjectMapper objectMapper = new ObjectMapper();
        return (Map<String,Object>)objectMapper.convertValue(param, Map.class);
    }
}

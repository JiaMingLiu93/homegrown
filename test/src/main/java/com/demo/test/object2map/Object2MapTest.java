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
        ObjectMapper objectMapper = new ObjectMapper();
        Object2MapEntity entity = new Object2MapEntity("liu", 18L);
        Map<String,Object> map = (Map<String,Object>)objectMapper.convertValue(entity, Map.class);
        System.out.println(map);
    }
}

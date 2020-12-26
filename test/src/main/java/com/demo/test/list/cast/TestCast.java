package com.demo.test.list.cast;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;

/**
 * @author youyu
 */
public class TestCast {
    public static void main(String[] args) {
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("1", Lists.newArrayList(1,2,null));
        Object o = map.get("1");

        List<Long> o1 = (List<Long>) o;

        System.out.println(o1);
    }
}

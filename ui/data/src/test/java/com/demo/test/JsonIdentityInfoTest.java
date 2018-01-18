package com.demo.test;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author jam
 * @description
 * @create 2018-01-18
 **/
public class JsonIdentityInfoTest {
    @Test
    public void jsonIdentityInfo() throws Exception {
        Parent parent = new Parent();
        parent.setName("jack");
        Child child = new Child();
        child.setName("mike");
        Child[] children = new Child[]{child};
        parent.setChildren(children);
        child.setParent(parent);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(parent);
//        Assert.assertEquals("{\"@id\":1,\"name\":\"jack\",\"children\":[{\"name\":\"mike\",\"parent\":1}]}",jsonStr);
        Assert.assertEquals("{\"@id\":\"1\",\"name\":\"jack\",\"children\":[{\"name\":\"mike\",\"parent\":{\"@ref\":\"1\"}}]}",jsonStr);
    }

//    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class,property = "@id")
    @JsonIdentityInfo(generator = JSOGGenerator.class)
    @Data
    public static class Parent{
        private String name;
        private Child[] children;
    }

    @Data
    public static class Child{
        private String name;
        private Parent parent;
    }


}

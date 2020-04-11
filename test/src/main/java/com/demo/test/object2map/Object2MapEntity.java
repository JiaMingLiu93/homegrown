package com.demo.test.object2map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jam
 * @description
 * @create 2018-10-23
 **/
@Data
@NoArgsConstructor
public class Object2MapEntity {
    private String name;
    private Long age;

    public Object2MapEntity(String name, Long age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

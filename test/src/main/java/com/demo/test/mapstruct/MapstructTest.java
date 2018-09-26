package com.demo.test.mapstruct;

import com.demo.test.mapstruct.entity.Ming;
import com.demo.test.mapstruct.entity.Person;
import org.mapstruct.factory.Mappers;

/**
 * @author jam
 * @description
 * @create 2018-09-07
 **/
public class MapstructTest {
    public static void main(String[] args) {
        PersonFrom mapper = Mappers.getMapper(PersonFrom.class);
        Ming ming = new Ming();
        ming.setName("ming");
        ming.setAge(18);
        Person person = mapper.from(ming);
        System.out.println(person);
    }
}

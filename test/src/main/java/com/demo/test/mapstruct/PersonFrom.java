package com.demo.test.mapstruct;

import com.demo.test.mapstruct.entity.Ming;
import com.demo.test.mapstruct.entity.Person;
import org.mapstruct.Mapper;

/**
 * @author jam
 * @description
 * @create 2018-09-07
 **/
@Mapper
public interface PersonFrom {
    Person from(Ming ming);
}

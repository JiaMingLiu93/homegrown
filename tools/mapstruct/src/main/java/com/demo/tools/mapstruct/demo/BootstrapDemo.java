package com.demo.tools.mapstruct.demo;

import org.mapstruct.factory.Mappers;

/**
 * @author youyu
 */
public class BootstrapDemo {
    public static void main(String[] args) {
        UserMapper mapper = Mappers.getMapper(UserMapper.class);
        User user = new User();
        user.setAge(18L);
        user.setName("jam");

        UserDTO dto = mapper.of(user);

        System.out.println(dto);
    }
}

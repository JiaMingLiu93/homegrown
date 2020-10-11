package com.demo.tools.mapstruct.demo;

import org.mapstruct.Mapper;

/**
 * @author youyu
 * @date 2020/9/2 1:45 PM
 */
@Mapper
public interface UserMapper {
    User ofDTO(UserDTO dto);
    UserDTO of(User user);
}

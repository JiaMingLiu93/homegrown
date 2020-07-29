package com.demo.springdiscover.springweb.demo.config;

import com.demo.springdiscover.springweb.demo.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author youyu
 */
@Configuration
@EnableWebMvc
public class WebConfiguration {
    @Bean
//    @RequestScope
//    @SessionScope
    @ApplicationScope
    public User user(){
        User user = new User();
        user.setId(1L);
        user.setName("Mr见面");
        return user;
    }
}

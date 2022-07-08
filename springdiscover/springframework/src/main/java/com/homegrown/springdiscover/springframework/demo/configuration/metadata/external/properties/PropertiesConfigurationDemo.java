package com.homegrown.springdiscover.springframework.demo.configuration.metadata.external.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

@PropertySource("classpath:/META-INF/usercommon-bean-definitions.properties")
public class PropertiesConfigurationDemo {

    @Bean
    public UserCommon userBeanName(@Value("${user.id}") Long id, @Value("${user.name}") String name){
        UserCommon userCommon = new UserCommon();
        userCommon.setId(id);
        userCommon.setName(name);
        return userCommon;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        Map<String,Object> propertySources = new HashMap<>();
        propertySources.put("user.name","jamFromHardCode");
        MapPropertySource mapPropertySource = new MapPropertySource("first-external-config", propertySources);
        context.getEnvironment().getPropertySources().addFirst(mapPropertySource);

        context.register(PropertiesConfigurationDemo.class);
        context.refresh();

        Map<String, UserCommon> usersMap = context.getBeansOfType(UserCommon.class);

        usersMap.forEach((key, value) -> System.out.printf("User Bean name : %s, content : %s \n", key, value));

        System.out.println(context.getEnvironment().getPropertySources());
        context.close();
    }
}

package com.demo.springdiscover.springframework.discover;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * @author youyu
 * @date 2020/7/4 9:13 AM
 */
public class RegisterBeanDefinitionAfterRefreshDemo {
    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 NoUniqueBeanDefinitionExceptionDemo 作为配置类（Configuration Class）
        applicationContext.register(RegisterBeanDefinitionAfterRefreshDemo.class);

        // 启动应用上下文
        applicationContext.refresh();
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        System.out.println("isConfigurationFrozen:"+((DefaultListableBeanFactory)autowireCapableBeanFactory).isConfigurationFrozen());

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(String.class);

        applicationContext.registerBeanDefinition("test",beanDefinitionBuilder.getBeanDefinition());
        System.out.println("isConfigurationFrozen:"+((DefaultListableBeanFactory)autowireCapableBeanFactory).isConfigurationFrozen());


        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(System.out::println);

        System.out.println("test bean is : "+applicationContext.getBean("test").toString());

        // 关闭应用上下文
        applicationContext.close();
    }

    @Bean
    public String bean1(){
        return "hello";
    }
}

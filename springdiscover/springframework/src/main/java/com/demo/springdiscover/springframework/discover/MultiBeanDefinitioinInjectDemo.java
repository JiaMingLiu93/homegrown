package com.demo.springdiscover.springframework.discover;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * @author youyu
 * @date 2020/7/2 1:50 PM
 */
public class MultiBeanDefinitioinInjectDemo {
    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类 NoUniqueBeanDefinitionExceptionDemo 作为配置类（Configuration Class）
        applicationContext.register(MultiBeanDefinitioinInjectDemo.class);
        applicationContext.register(ConfigurationClass.class);
        // 启动应用上下文
        applicationContext.refresh();

        // 关闭应用上下文
        applicationContext.close();
    }

    @Bean
    @Primary
    public String bean1(){
        System.out.println("bean1");
        return "bean1";
    }

    static class ConfigurationClass{
        @Bean
        public String bean1(){
            System.out.println("bean2");
            return "bean2";
        }
    }

}

package overwrite.demo.service;

import overwrite.spring.annotation.HomeService;

/**
 * @author jam
 * @description
 * @create 2018-09-26
 **/
@HomeService
public class DemoService {
    public void sayHello(){
        System.out.println("hello,jam!");
    }
}

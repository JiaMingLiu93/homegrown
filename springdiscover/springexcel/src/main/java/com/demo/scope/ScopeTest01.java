package com.demo.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author jam
 * @description
 * @create 2018-05-22
 **/
@Component
@Scope("prototype")
public class ScopeTest01 {
    public void print(){
        System.out.println("prototype test.");
    }
}

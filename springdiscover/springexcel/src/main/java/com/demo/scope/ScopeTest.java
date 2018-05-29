package com.demo.scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jam
 * @description
 * @create 2018-05-22
 **/
@RestController
@RequestMapping("test")
public class ScopeTest {
    @Autowired
    ScopeTest01 scopeTest01;

    @GetMapping("/get")
    @ResponseBody
    public String print(){
        scopeTest01.print();
        return "get";
    }
}

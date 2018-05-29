package com.demo.param;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jam
 * @description
 * @create 2018-05-29
 **/
@RestController
@RequestMapping("param")
public class ControllerParamTest {
    @GetMapping("/list")
    public String test(@RequestParam List<Integer> ids){
        return ""+ids;
    }
}

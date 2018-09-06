package com.demo.springmvcview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jam
 * @description
 * @create 2018-08-29
 **/
@Controller
public class ViewDemo {
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("message","hello,world!");
        return "index";
    }
}

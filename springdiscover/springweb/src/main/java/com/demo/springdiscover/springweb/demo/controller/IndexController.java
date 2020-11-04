package com.demo.springdiscover.springweb.demo.controller;

import com.demo.springdiscover.springweb.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author youyu
 */
@Controller
public class IndexController {

    @Autowired
    private User user;

    @GetMapping("/index.html")
    public String index(Model model){
        // userObject -> 渲染上下文
        // user 对象存在 ServletContext，上下文名称：scopedTarget.user == 新生成 Bean 名称
        model.addAttribute("userObject",user);
        return "index";
    }
}

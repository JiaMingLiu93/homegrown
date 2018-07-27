package com.demo.oauth.web.controller;

import com.demo.oauth.authentic.service.IOauthService;
import com.demo.oauth.authentic.shiro.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author jam
 * @description
 * @create 2018-07-03
 **/
@RestController
public class LoginController {
    @Autowired
    private IOauthService oauthService;

    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }

    @GetMapping("/index")
    public ModelAndView index(){
        return new ModelAndView("index");
    }

    @GetMapping("/redirect")
    public void getRedirect(@RequestParam String redirectUrl,HttpServletResponse resp) throws IOException {
        resp.sendRedirect(oauthService.getRedirectUrl(redirectUrl, UUID.randomUUID().toString()));
    }
    @PostMapping
    public String login(String code,String redirectUrl){
        AuthToken token = oauthService.getToken(code, redirectUrl);
        return "success";
    }
}

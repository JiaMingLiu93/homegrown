package com.demo.oauth.authentic.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author jam
 * @description
 * @create 2018-06-28
 **/
public class AuthToken implements AuthenticationToken {
    private String token;
    public AuthToken(String token){
        this.token = token;
    }
    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }
}

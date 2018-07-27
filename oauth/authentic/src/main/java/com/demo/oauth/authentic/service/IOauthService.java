package com.demo.oauth.authentic.service;

import com.demo.oauth.authentic.shiro.AuthToken;

/**
 * @author jam
 * @description
 * @create 2018-06-28
 **/
public interface IOauthService {
    /**
     * 根据token获取用户信息
     * @param accessToken
     * @return
     */
//    UserVO getUser(String accessToken);

    /**
     * 根据code获取token
     * @param code
     * @param redirectUrl
     * @return
     */
    AuthToken getToken(String code, String redirectUrl);

    /**
     * 退出
     * @param token
     * @return
     */
    String logout(String token);

    /**
     * 重定向
     * @param redirectUrl
     * @param state
     * @return
     */
    String getRedirectUrl(String redirectUrl, String state);
}

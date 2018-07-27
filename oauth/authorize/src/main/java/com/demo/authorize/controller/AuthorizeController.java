package com.demo.authorize.controller;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jam
 * @description
 * @create 2018-07-03
 **/
@Controller
public class AuthorizeController {
    Logger  log = LoggerFactory.getLogger(AuthorizeController.class);
    @GetMapping("/login")
    public String login(){
        return "index";
    }
    @PostMapping("/token")
    public HttpEntity token(HttpServletRequest request) throws OAuthProblemException, OAuthSystemException {
        OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
        log.info("client_id = "+oauthRequest.getClientId());
        log.info("secret = "+oauthRequest.getClientSecret());
        String authCode = oauthRequest.getParam(OAuth.OAUTH_CODE);
        log.info("authCode = "+authCode);

        if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())) {
            if (!checkAuthCode(authCode)) {
                OAuthResponse response = OAuthASResponse
                        .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_GRANT)
                        .setErrorDescription("")
                        .buildJSONMessage();
                return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }
        }

        //generate token
        //authCode is relative with token at redis.
        OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
        String accessToken = oauthIssuerImpl.accessToken();

        OAuthResponse response = OAuthASResponse
                .tokenResponse(HttpServletResponse.SC_OK)
                .setAccessToken(accessToken)
                .setExpiresIn(String.valueOf(10))
                .buildJSONMessage();

        return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));


    }

    private boolean checkAuthCode(String authCode) {
        //check if exceed the expire time of authCode
        return true;
    }

    @PostMapping("/authorize")
    @ResponseBody
    public Result authorize(HttpServletRequest request) throws OAuthSystemException{

        try {
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
            Preconditions.checkNotNull(Strings.emptyToNull(oauthRequest.getClientId()), "ClientId");
            Preconditions.checkNotNull(Strings.emptyToNull(oauthRequest.getResponseType()), "ResponseType");
            Preconditions.checkNotNull(Strings.emptyToNull(oauthRequest.getRedirectURI()), "redirectURI");

            if (!oauthRequest.getClientId().equals("client")) {
                OAuthResponse response =
                        OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                                .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                                .setErrorDescription("invalid client id.")
                                .buildJSONMessage();
                throw new RuntimeException(response.getBody().toString());
            }

            //check username and password
            log.info("username="+request.getParameter("username"));
            log.info("password="+new String(Base64.decode(request.getParameter("password"))));

            //generate authorize code
            String authorizationCode = null;
            //responseType only support code model
            String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
            if (responseType.equals(ResponseType.CODE.toString())) {
                OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
                authorizationCode = oauthIssuerImpl.authorizationCode();
            }

            OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                    OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);

            //设置授权码
            builder.setCode(authorizationCode);
            //得到到客户端重定向地址
            String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);

            //构建响应
            final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();

            Result result = new Result();
            result.setResult(response.getLocationUri());
            return result;

        }  catch (OAuthProblemException e) {
            String redirectUri = e.getRedirectUri();
            if (OAuthUtils.isEmpty(redirectUri)) {
                throw new RuntimeException("invalid redirect url.");
            }
            throw new RuntimeException(e.getDescription());
        }
    }



    class Result{
        public Result(){}
        private int status = 1;
        private String result;

        public String getResult() {
            return result;
        }
        public void setResult(String result){
            this.result = result;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}

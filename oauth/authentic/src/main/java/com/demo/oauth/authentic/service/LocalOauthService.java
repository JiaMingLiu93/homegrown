package com.demo.oauth.authentic.service;

import com.demo.oauth.authentic.shiro.AuthToken;
import com.google.common.base.Strings;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author jam
 * @description
 * @create 2018-06-28
 **/
@Service
public class LocalOauthService implements IOauthService {
    @Override
    public AuthToken getToken(String code, String redirectUrl) {
        try {
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation("http://localhost:9091/token")
                    .setClientId("client")
                    .setClientSecret("secret")
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setCode(code)
                    .setRedirectURI(redirectUrl)
                    .buildBodyMessage();
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthJSONAccessTokenResponse accessToken = oAuthClient.accessToken(request, OAuth.HttpMethod.POST);
            return new AuthToken(accessToken.getAccessToken());
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String logout(String token) {
        return null;
    }

    @Override
    public String getRedirectUrl(String redirectUrl, String state) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:9091/login")
                .queryParam("client_id", "client")
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", redirectUrl);
        if (!Strings.isNullOrEmpty(state)) {
            builder.queryParam("state", state);
        }
        return builder.build().encode().toUriString();
    }
}

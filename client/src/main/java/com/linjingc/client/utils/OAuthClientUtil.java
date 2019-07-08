package com.linjingc.client.utils;

import lombok.Data;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 请求oAuth授权工具类
 *
 * @author cxc
 */
@Component
@Data
@ConfigurationProperties(prefix = "basic.oauth2")
public class OAuthClientUtil {
    private String authorizationUrl;
    private String clientId;
    private String secret;
    private String scope;


    private static Logger logger = LoggerFactory.getLogger(OAuthClientUtil.class.getName());

    /**
     * 获取token
     * @param username
     * @param password
     * @return
     * @throws OAuthProblemException
     */
    public String getApiToken(String username, String password)throws OAuthProblemException {
        logger.info("api getApiToken");
        String accessToken = null;
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        try {
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(authorizationUrl)
                    .setGrantType(GrantType.PASSWORD)
                    .setUsername(username)
                    .setPassword(password)
                    .setClientId(clientId)
                    .setClientSecret(secret)
                    .setScope(scope)
                    .buildQueryMessage();

            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json");

            OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(request, OAuth.HttpMethod.POST); //去服务端请求access_token，并返回响应
            accessToken = oAuthResponse.getAccessToken(); //获取服务端返回过来的access_token
            logger.info("api token: " + accessToken);
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    /**
     * 刷新token
     * @param token
     * @return
     * @throws OAuthProblemException
     */
    public String refreshToken(String token) throws OAuthProblemException {
        String accessToken = null;

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        try {
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(authorizationUrl)
                    .setGrantType(GrantType.REFRESH_TOKEN)
                    .setClientId(clientId)
                    .setClientSecret(secret)
                    .setScope(scope)
                    .setRefreshToken(token)
                    .buildQueryMessage();

            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json");

            OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(request, OAuth.HttpMethod.POST); //去服务端请求access_token，并返回响应
            accessToken = oAuthResponse.getAccessToken(); //获取服务端返回过来的access_token
            logger.info("api token: " + accessToken);
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

}
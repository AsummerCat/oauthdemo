package com.linjingc.authentication.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 获取客户端信息的信息类
 */
@Component
public class MyClientDetailsService implements ClientDetailsService {
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {


        if (clientId.equals("client_1")) {
            MyClientDetails myClientDetails = new MyClientDetails();
            //客户端Id
            myClientDetails.setClientId("client_1");
            //resourceId 用于分配可以访问的资源服务
            Set<String> resourceIds = new HashSet<>();
            resourceIds.add("order");
            myClientDetails.setResourceIds(resourceIds);
            //认证类型
            Set<String> authorizedGrantTypes = new HashSet<>();
            authorizedGrantTypes.add("client_credentials");
            authorizedGrantTypes.add("refresh_token");
            myClientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);

            //使用范围
            HashSet<String> scope = new HashSet<>();
            scope.add("select");
            scope.add("heihei");
            myClientDetails.setScope(scope);

            //权限信息
            List<GrantedAuthority> auths = new ArrayList<>();
            //这里添加权限 可以添加多个权限
            auths.add(new SimpleGrantedAuthority("add"));
            auths.add(new SimpleGrantedAuthority("update"));
            auths.add(new SimpleGrantedAuthority("delete"));
            auths.add(new SimpleGrantedAuthority("USER"));
            myClientDetails.setAuthorities(auths);

            //秘钥
            myClientDetails.setClientSecret("123456");

            //过期时间
            myClientDetails.setAccessTokenValiditySeconds(10000);
            myClientDetails.setRefreshTokenValiditySeconds(30000);
            return myClientDetails;
        }

        return null;
    }
}

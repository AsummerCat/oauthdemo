package com.linjingc.resources.config;

import com.linjingc.resources.config.errormsg.AuthExceptionEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;


/**
 * 配置资源服务器
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    private static final String DEMO_RESOURCE_ID = "order";

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    //内部关联了ResourceServerSecurityConfigurer和HttpSecurity。前者与资源安全配置相关，后者与http安全配置相关
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //resourceId 用于分配给可授予的clientId
        //stateless  标记以指示在这些资源上仅允许基于令牌的身份验证
        //tokenStore token的存储方式
        resources.resourceId(DEMO_RESOURCE_ID).stateless(true).tokenStore(new RedisTokenStore(redisConnectionFactory));

        //认证异常流程处理返回
        resources.authenticationEntryPoint(new AuthExceptionEntryPoint());
        // .accessDeniedHandler(CustomAccessDeniedHandler);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        http.authorizeRequests()
                //开启路径不需要权限访问
                .antMatchers("/").permitAll()
                //其他路径都需要权限
                .anyRequest().authenticated();
    }
}

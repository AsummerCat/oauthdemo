package com.linjingc.authentication.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 授权服务
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;


    /**
     * 声明单个客户端及其属性 最少一个 不然无法启动
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //这个地方后面会使用到 前缀表示密码加密类型
        String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode("123456");


//        clients
//                //内存模式
//                .inMemory()
//                .withClient("client_2")
//                .resourceIds(DEMO_RESOURCE_ID)
//                .authorizedGrantTypes("password", "refresh_token")
//                .scopes("select")
//                .authorities("oauth2")
//                .secret("123456")
//                .accessTokenValiditySeconds(100)
//                .refreshTokenValiditySeconds(100);

        //todo 查询数据库 或者查询内容只能选中一个
        //查询数据库
        clients.withClientDetails(new MyClientDetailsService());

    }

    /**
     * 配置授权服务器终结点的非安全功能，如令牌存储、令牌自定义、用户批准和授予类型 请求方式
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(new RedisTokenStore(redisConnectionFactory))
                .authenticationManager(authenticationManager)
                //允许 GET、POST 请求获取 token，即访问端点：oauth/token
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }


    /**
     * 配置（授权服务器安全配置器安全）
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许表单认证
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
        //自定义授权失败(forbidden)时返回信息
        security.accessDeniedHandler(new CustomAccessDeniedHandler());
    }


}

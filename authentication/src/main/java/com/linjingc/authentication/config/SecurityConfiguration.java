package com.linjingc.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Security配置类
 * @author cxc
 * @date  2019年7月11日16:38:56
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        //BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        password 方案一：明文存储，用于测试，不能用于生产
//        String finalPassword = "123456";
//        password 方案二：用 BCrypt 对密码编码
//        String finalPassword = bCryptPasswordEncoder.encode("123456");
        // password 方案三：支持多种编码，通过密码的前缀区分编码方式
        //String finalPassword = "{bcrypt}"+bCryptPasswordEncoder.encode("123456");
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user_1").password("123456").authorities("USER").build());
        manager.createUser(User.withUsername("user_2").password("123456").authorities("USER").build());
        return manager;
    }

    /**
     * 这一步的配置是必不可少的，否则SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // password 方案一：明文存储，用于测试，不能用于生产
    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // password 方案二：用 BCrypt 对密码编码
    //@Bean
    //PasswordEncoder passwordEncoder(){
    //    return new BCryptPasswordEncoder();
    //}

    // password 方案三：支持多种编码，通过密码的前缀区分编码方式,推荐
    //@Bean
    //PasswordEncoder passwordEncoder() {
    //    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    //}


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //开启路径不需要权限访问
                .antMatchers("/oauth/*", "/", "/error/*").permitAll()
                //其他路径都需要权限
                .anyRequest().authenticated();
    }


    /**
     * 放行静态资源
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/js/**");
        web.ignoring().antMatchers("/images/**");
        web.ignoring().antMatchers("/login/**");
        //解决服务注册url被拦截的问题
        web.ignoring().antMatchers("/resources/**");

    }
}
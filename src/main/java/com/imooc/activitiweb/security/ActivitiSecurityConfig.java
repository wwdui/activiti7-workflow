package com.imooc.activitiweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class ActivitiSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

//----------------自定义方法登录----------------------
       /* http
                .formLogin()
                .loginPage("/authentication/require")//这个页面必须要放在resources/resources里，否则提交不会生效，估计是安全框架的机制
                .loginProcessingUrl("/authentication/require")//这里的名字和登录页的Post内容一致，就可以调框架自带的登录
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                //.defaultSuccessUrl("/hello")
                //.successForwardUrl("success.html")
                //.failureForwardUrl("failure.html")
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require", "/demo-login.html", "/demo-login1.html", "/demo-login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .headers().frameOptions().disable()//让frame页面可以正常使用
                .and()
                .csrf().disable();*/
        //--------------------------网页登录-----------------------------
               /* http
                .formLogin()
                .loginPage("/demo-login.html")//这个页面必须要放在resources/resources里，否则提交不会生效，估计是安全框架的机制
                .loginProcessingUrl("/demo-login")//这里的名字和登录页的Post内容一致，就可以调框架自带的登录
                //.defaultSuccessUrl("/hello")
                //.successForwardUrl("success.html")
                //.failureForwardUrl("failure.html")
                .and()
                .authorizeRequests()
                .antMatchers("/demo-login.html", "/demo-login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
                */
        //--------------------------验证都关闭-----------------------------
               //http.authorizeRequests().anyRequest().permitAll().and().logout().permitAll().and().csrf().disable().headers().frameOptions().disable();//全部页面不验证
        //--------------------------activiti与layui的自定义登录-----------------------------
        http
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                //.defaultSuccessUrl("/hello")
                //.successForwardUrl("success.html")
                //.failureForwardUrl("failure.html")
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/demo-login.html", "/demo-login1.html", "/layuimini/page/login-1.html").permitAll()
                .anyRequest()
                .permitAll().and().logout().permitAll().and().csrf().disable().headers().frameOptions().disable();//全部页面不验证


    }


}

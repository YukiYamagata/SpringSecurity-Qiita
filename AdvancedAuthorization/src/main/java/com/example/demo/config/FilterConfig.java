package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.filter.AuthFilter;
import com.example.demo.service.AuthUtil;

@Configuration
public class FilterConfig {

    private final AuthUtil authUtil;

    @Autowired
    public FilterConfig(AuthUtil authUtil) {
        this.authUtil = authUtil;
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter() {
        // FilterをnewしてFilterRegistrationBeanのコンストラクタに渡す
        FilterRegistrationBean<AuthFilter> bean
            = new FilterRegistrationBean<>(new AuthFilter(authUtil)); // --- AuthFilterをBeanに登録

        // Filterのurl-patternを指定
        bean.addUrlPatterns("/*"); // --- すべての通信をAuthFilterに通過させる

        // Filterの実行順序設定
        bean.setOrder(-99); // --- Spring SecurityのFilterの次に設定

        return bean;
    }
}
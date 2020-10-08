package com.example.demo.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;

import com.example.demo.service.AccountUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // メソッド認可処理を有効化
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountUserDetailsService userDetailsService;

    @Autowired
    AccessDecisionVoter<FilterInvocation> myVoter;

    public AccessDecisionManager createAccessDecisionManager() {
        return new AffirmativeBased(Arrays.asList(myVoter)); // 認可処理はAffirmativeBased、投票処理はMyVoterを使用する。
    }

    PasswordEncoder passwordEncoder() {
        //BCryptアルゴリズムを使用してパスワードのハッシュ化を行う
        return new BCryptPasswordEncoder(); // BCryptアルゴリズムを使用
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // AuthenticationManagerBuilderに、実装したUserDetailsServiceを設定する
        auth.userDetailsService(userDetailsService)     // 作成したUserDetailsServiceを設定
                .passwordEncoder(passwordEncoder());    // パスワードのハッシュ化方法を指定(BCryptアルゴリズム)
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 認可の設定
        http.exceptionHandling()
                .accessDeniedPage("/accessDeniedPage")  // アクセス拒否された時に遷移するパス
            .and()
            .authorizeRequests()
                .antMatchers("/**").authenticated()
                    .accessDecisionManager(createAccessDecisionManager()); // すべてのアクセスにおいて、認可処理の適用

        // ログイン設定
        http.formLogin()                                // フォーム認証の有効化
                .loginPage("/loginForm")                // ログインフォームを表示するパス
                .loginProcessingUrl("/authenticate")    // フォーム認証処理のパス
                .usernameParameter("userName")          // ユーザ名のリクエストパラメータ名
                .passwordParameter("password")          // パスワードのリクエストパラメータ名
                .defaultSuccessUrl("/home")             // 認証成功時に遷移するデフォルトのパス
                .failureUrl("/loginForm?error=true");   // 認証失敗時に遷移するパス

        // ログアウト設定
        http.logout()
                .logoutSuccessUrl("/loginForm")         // ログアウト成功時に遷移するパス
                .permitAll();                           // 全ユーザに対してアクセスを許可
    }
}
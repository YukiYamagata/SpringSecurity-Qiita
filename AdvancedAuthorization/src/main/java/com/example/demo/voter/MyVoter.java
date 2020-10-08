package com.example.demo.voter;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import com.example.demo.service.AccountUserDetails;
import com.example.demo.service.AuthorizationService;

@Component
public class MyVoter implements AccessDecisionVoter<FilterInvocation> {

    private final AuthorizationService authorizationService;

    @Autowired
    public MyVoter(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {    // 投票処理が必要か不要か判定するメソッド
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {               // 投票処理が必要か不要か判定するメソッド
        return true;
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation filterInvocation,
            Collection<ConfigAttribute> attributes) {       // アクセス権を付与するかどうか投票するメソッド

        HttpServletRequest request = filterInvocation.getHttpRequest(); // HttpServletRequestの取得
        String uri = request.getRequestURI();                           // リクエストからURIを取得

        if(authorizationService.isAuthorized("*", uri)) {   // 全てのRoleにアクセス許可されているか判定
            return ACCESS_GRANTED;
        }

        Object principal = authentication.getPrincipal();   // ユーザの識別情報の取得

        if (!principal.getClass().equals(AccountUserDetails.class)) { // 取得した識別情報がAccountUserDetailsかどうか判定
            return ACCESS_DENIED;
        }

        String roleName = ((AccountUserDetails) principal).getUser().getRoleName(); // ユーザのRoleの取得

        if(authorizationService.isAuthorized(roleName, uri)) { // 取得したRoleがアクセス許可されているか判定
            return ACCESS_GRANTED;
        }

        return ACCESS_DENIED;
    }
}
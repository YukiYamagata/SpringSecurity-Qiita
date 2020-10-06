package com.example.demo.authorization;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import com.example.demo.service.AccountUserDetails;
import com.example.demo.service.AuthUtil;

@Component
public class MyVoter implements AccessDecisionVoter<FilterInvocation> {

	private final AuthUtil authUtil;

	@Autowired
	public MyVoter(AuthUtil authUtil) {
		this.authUtil = authUtil;
	}

	@Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation filterInvocation, Collection<ConfigAttribute> attributes) {

    	HttpServletRequest request = filterInvocation.getHttpRequest();
    	String uri = request.getRequestURI();

    	// 全てのRoleにアクセス許可されているか判定
    	if(authUtil.isAuthorized("*", uri)) {
        	return ACCESS_GRANTED;
        }

    	// 資格情報の取得
		Object principal = authentication.getPrincipal();

		if (!principal.getClass().equals(AccountUserDetails.class)) {
			return ACCESS_DENIED;
		}

		// ユーザのRoleの取得
		String roleName = ((AccountUserDetails) principal).getUser().getRoleName();

		// 取得したRoleがアクセス許可されているか判定
		if(authUtil.isAuthorized(roleName, uri)) {
        	return ACCESS_GRANTED;
        }

    	return ACCESS_DENIED;
    }
}
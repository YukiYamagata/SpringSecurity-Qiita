package com.example.demo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.AccountUserDetails;
import com.example.demo.service.AuthUtil;

public class AuthFilter extends OncePerRequestFilter {
    private final AuthUtil authUtil;

    public AuthFilter(AuthUtil authUtil) {
        this.authUtil = authUtil;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        if(authUtil.isAuthorized("*", uri)) { // 全てのRoleにアクセス許可されているか判定
        	filterChain.doFilter(request, response);
        	return ;
        }

        HttpSession session = request.getSession(false); // HttpSessionの取得

        if (session != null) {
            SecurityContextImpl sci
                = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT"); // SecurityContextオブジェクトの取得

            if (sci != null) {
                Authentication auth = sci.getAuthentication(); // Authenticationオブジェクトの取得

                if (auth != null) {
                    AccountUserDetails principal
                        = (AccountUserDetails) auth.getPrincipal(); // AccountUserDetailsの取得

                    if (principal != null) {
                        String roleName = principal.getUser().getRoleName(); // ユーザのRoleの取得

                        if(authUtil.isAuthorized(roleName, uri)) { // 取得したRoleがアクセス許可されているか判定
                        	filterChain.doFilter(request, response);
                        	return ;
                        }
                    }
                }
            }
        }

        throw new AccessDeniedException("Unauthorized Access");
    }
}
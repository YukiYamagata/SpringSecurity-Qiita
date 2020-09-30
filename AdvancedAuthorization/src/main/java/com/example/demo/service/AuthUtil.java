package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.entity.AccessAuthorization;
import com.example.demo.repository.AccessAuthorizationDao;

@Service
public class AuthUtil {
    private final AccessAuthorizationDao authDao;

    @Autowired
    public AuthUtil(AccessAuthorizationDao authDao) {
        this.authDao = authDao;
    }

    public boolean isAuthorized(String roleName, String uri) { // アクセス許可されているか判定するメソッド

        if (StringUtils.isEmpty(roleName)) {
            throw new IllegalArgumentException("RoleNameが空です。");
        }

        if (StringUtils.isEmpty(uri)) {
            throw new IllegalArgumentException("URIが空です。");
        }

        //AccessAuthorization一件を取得 AccessAuthorizationが無ければ例外発生
        try {
            AccessAuthorization auth = authDao.find(roleName, uri); // AccessAuthorizationインスタンスを取得

            if (auth != null) { // アクセス許可
                return true;

            } else {            // アクセス拒否
                return false;

            }
        } catch (EmptyResultDataAccessException e) { // アクセス拒否
            return false;
        }
    }
}
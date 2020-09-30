package com.example.demo.repository;

import com.example.demo.entity.AccessAuthorization;

public interface AccessAuthorizationDao {
    AccessAuthorization find(String roleName, String uri);
}
package com.example.demo.service;

public interface AuthorizationService {
	boolean isAuthorized(String roleName, String uri);
}

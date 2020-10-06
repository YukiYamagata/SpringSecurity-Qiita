package com.example.demo.service;

public interface AuthUtil {
	boolean isAuthorized(String roleName, String uri);
}

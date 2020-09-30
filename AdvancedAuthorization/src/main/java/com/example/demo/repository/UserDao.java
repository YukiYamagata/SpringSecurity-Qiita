package com.example.demo.repository;

import com.example.demo.entity.MyUser;

public interface UserDao {
    MyUser findUserByUserName(String userName);
}
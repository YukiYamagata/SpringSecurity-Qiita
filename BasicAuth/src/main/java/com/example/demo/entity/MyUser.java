package com.example.demo.entity;

import java.io.Serializable;

public class MyUser implements Serializable{
    private String userName;    // H2DBにおける、usersテーブルの"username"を格納するフィールド

    private String password;    // H2DBにおける、usersテーブルの"password"を格納するフィールド

    private String name;        // H2DBにおける、usersテーブルの"name"を格納するフィールド

    private String roleName;    // H2DBにおける、usersテーブルの"roleName"を格納するフィールド

    /**
     * getter, setter
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
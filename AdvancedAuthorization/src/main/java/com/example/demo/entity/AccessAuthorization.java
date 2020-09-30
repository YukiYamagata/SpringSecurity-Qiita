package com.example.demo.entity;

import java.io.Serializable;

public class AccessAuthorization implements Serializable {
    String roleName;    // H2DBにおける、access_authorizationテーブルの"rolename"を格納するフィールド
    String uri;         // H2DBにおける、access_authorizationテーブルの"uri"を格納するフィールド

    /**
     * getter, setter
     */
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
}
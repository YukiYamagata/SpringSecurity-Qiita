package com.example.demo.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AccessAuthorization;

@Repository
public class AccessAuthorizationDaoImpl implements AccessAuthorizationDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AccessAuthorizationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * roleNameとuriを検索条件にSELECT文を実行して、DBに登録されているか検索する
     * @param roleName
     * @param uri
     * @return AccessAuthorization
     */
    @Override
    public AccessAuthorization find(String roleName, String uri) {
        String sql = "SELECT rolename, uri FROM access_authorization WHERE rolename = ? AND uri = ?";

        //ユーザを一件取得
        Map<String, Object> result = jdbcTemplate.queryForMap(sql, roleName, uri);

        // Entityクラス(User型)に変換
        AccessAuthorization auth = convMapToAccessAuthorization(result);

        return auth;
    }

    /**
     * SQL SELECT文を実行した結果(Map<String, Object>)をAccessAuthorization型に変換する
     * @param Map<String, Object>
     * @return AccessAuthorization
     */
    private AccessAuthorization convMapToAccessAuthorization(Map<String, Object> map) {
        AccessAuthorization auth = new AccessAuthorization();

        auth.setRoleName((String) map.get("rolename"));
        auth.setUri((String) map.get("uri"));

        return auth;
    }
}
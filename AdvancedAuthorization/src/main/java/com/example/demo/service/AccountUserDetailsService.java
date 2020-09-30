package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.MyUser;
import com.example.demo.repository.UserDao;

@Service
public class AccountUserDetailsService implements UserDetailsService {
    private final UserDao userDao;

    @Autowired
    public AccountUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {          // データベースからアカウント情報を検索するメソッド

        if (userName == null || "".equals(userName)) {
            throw new UsernameNotFoundException(userName + "is not found");
        }

        //User一件を取得 userNameが無ければ例外発生
        try {
            //Userを取得
            MyUser myUser = userDao.findUserByUserName(userName);

            if (myUser != null) {
                return new AccountUserDetails(myUser); // UserDetailsの実装クラスを生成

            } else {
                throw new UsernameNotFoundException(userName + "is not found");
            }

        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException(userName + "is not found");
        }
    }
}
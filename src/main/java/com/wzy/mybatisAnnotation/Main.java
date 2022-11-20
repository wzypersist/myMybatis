package com.wzy.mybatisAnnotation;

import com.wzy.mybatisAnnotation.entity.User;
import com.wzy.mybatisAnnotation.mapper.UserMapper;
import com.wzy.mybatisAnnotation.sqlsession.SqlSession;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserMapper userMapper = SqlSession.getMapper(UserMapper.class);
        System.out.println(userMapper.insertUser("hh", "hhh"));
        List<User> users = userMapper.selectList();
        for (User user : users) {
            System.out.println(user);
        }
    }
}

package com.wzy.mybatisXML.mapper;

import com.wzy.mybatisXML.bean.User;

import java.util.List;

public interface UserMapper {
    /**
     * 获取单个user
     *
     * @param id
     * @return
     * @see
     */
    User getUser(String id);

    User selectUser( String id,String name);
    
    /**
     * 获取所有用户
     *
     * @return
     * @see
     */
    List<User> getAll();

    /**
     * 更新用户（功能未完成）
     *
     * @param id
     */
    void updateUser(String id);
    
    void insert(User user);
}

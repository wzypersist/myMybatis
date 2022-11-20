package com.wzy.mybatisAnnotation.mapper;

import com.wzy.mybatisAnnotation.annotation.Insert;
import com.wzy.mybatisAnnotation.annotation.Param;
import com.wzy.mybatisAnnotation.annotation.Query;
import com.wzy.mybatisAnnotation.entity.User;

import java.util.List;


public interface UserMapper {
    
    @Query("select username, city from user  where username = #{username} and city = #{city}")
    User selectUser(@Param("username") String name, @Param("city") String city);
    
    @Query("select * from user")
    List<User> selectList();
    
    @Insert("insert into user(username, city) values(#{username}, #{city})")
    int insertUser(@Param("username") String name, @Param("city") String city);
}

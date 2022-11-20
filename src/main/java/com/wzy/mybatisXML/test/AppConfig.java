package com.wzy.mybatisXML.test;

import com.wzy.mybatisXML.mybatisSpring.MapperScan;
import com.wzy.mybatisXML.sqlsession.SqlSessionFactory;
import com.wzy.mybatisXML.sqlsession.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan("com.wzy.mybatisXML")
@MapperScan("com.wzy.mybatisXML.mapper")
public class AppConfig {
    
    @Bean
    public SqlSessionFactory sqlSessionFactory(){
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build("conf.properties");
        return sqlSessionFactory;
    }
    
    
}

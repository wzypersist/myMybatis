package com.wzy.mybatisXML.sqlsession;

import com.wzy.mybatisXML.sqlsession.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSession;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    
    public SqlSessionFactory build(String fileName){
        InputStream inputStream = SqlSessionFactoryBuilder.class.getClassLoader().getResourceAsStream(fileName);
        return build(inputStream);
    }

    private SqlSessionFactory build(InputStream inputStream) {
        try {
            Configuration.PROPS.load(inputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
        return new DefaultSqlSessionFactory(new Configuration());
        
    }

}

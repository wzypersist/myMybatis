package com.wzy.mybatisXML.mybatisSpring;

import com.wzy.mybatisXML.sqlsession.SqlSession;
import com.wzy.mybatisXML.sqlsession.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

public class MapperFactoryBean implements FactoryBean {
    
    private Class mapperInterface;
    
    private SqlSession sqlSession;

    public MapperFactoryBean(Class mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Autowired
    public void setSqlSession(SqlSessionFactory sqlSessionFactory){
        sqlSessionFactory.openSession().getConfiguration().addMapper(mapperInterface);
        this.sqlSession = sqlSessionFactory.openSession();
    }
    
    @Override
    public Object getObject() throws Exception {
        return sqlSession.getMapper(mapperInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }
}

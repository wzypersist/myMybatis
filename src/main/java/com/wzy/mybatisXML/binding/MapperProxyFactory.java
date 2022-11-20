package com.wzy.mybatisXML.binding;

import com.wzy.mybatisXML.sqlsession.SqlSession;

import java.lang.reflect.Proxy;

public class MapperProxyFactory<T> {
    
    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    /**
     * 根据sqlSession创建代理
     * @param sqlSession
     * @return
     */
    public T newInstance(SqlSession sqlSession) {
        MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, this.mapperInterface);
        return newInstance(mapperProxy);
    }

    private T newInstance(MapperProxy<T> mapperProxy) {
        return (T) Proxy.newProxyInstance(this.mapperInterface.getClassLoader(), new Class[]{this.mapperInterface},
                mapperProxy);
    }
}

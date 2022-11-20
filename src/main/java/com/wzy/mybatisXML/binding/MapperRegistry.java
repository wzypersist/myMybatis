package com.wzy.mybatisXML.binding;

import com.wzy.mybatisXML.sqlsession.SqlSession;

import java.util.HashMap;
import java.util.Map;

public class MapperRegistry {
    
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    /**
     * 注册代理工厂
     * @param type
     * @param <T>
     */
    public <T> void addMapper(Class<T> type){this.knownMappers.put(type,new MapperProxyFactory<>(type));}
    
    public <T> T getMapper(Class<T> type, SqlSession sqlSession){
        MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) this.knownMappers.get(type);
        return mapperProxyFactory.newInstance(sqlSession);
        
    }
    
}

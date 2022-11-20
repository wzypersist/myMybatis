package com.wzy.mybatisXML.sqlsession;


import com.wzy.mybatisXML.binding.MapperRegistry;
import com.wzy.mybatisXML.executor.statement.MappedStatement;



import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Configuration {
    
    public static Properties PROPS = new Properties();
    
    protected final MapperRegistry mapperRegistry = new MapperRegistry();
    
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();
    
    public static String getProperty(String key){
        return getProperty(key,"");
    }

    private static String getProperty(String key, String defaultValue) {
        return PROPS.containsKey(key) ? PROPS.getProperty(key) : defaultValue;
    }

    public void addMappedStatement(String sqlId, MappedStatement statement) {
        mappedStatements.put(sqlId,statement);
    }

    public MappedStatement getMappedStatement(String id) {
        return this.mappedStatements.get(id);
    }
    
    public <T> T getMapper(Class<T> type,SqlSession sqlSession){
        return this.mapperRegistry.getMapper(type,sqlSession);
    }
    
    public <T> void addMapper(Class<T> type){
        this.mapperRegistry.addMapper(type);
    }
}

package com.wzy.mybatisXML.sqlsession;


import java.util.List;

public interface SqlSession {

    /**
     * 获取mapper
     * @param paramClass
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<T> paramClass);

    /**
     * 获取配置类
     * @return
     */
    Configuration getConfiguration();

    /**
     * 查询单条记录
     * @param statementId
     * @param paramter
     * @param <T>
     * @return
     */
    <T> T selectOne(String statementId,Object paramter);

    /**
     * 查询多条记录
     * @param statementId
     * @param paramter
     * @param <E>
     * @return
     */
    <E> List<E> selectList(String statementId,Object paramter);
    
    void update(String statementId,Object paramter);
    
    void insert(String statementId,Object paramter);
    
    
}

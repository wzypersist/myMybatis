package com.wzy.mybatisXML.sqlsession.defaults;

import com.wzy.mybatisXML.executor.Executor;
import com.wzy.mybatisXML.executor.SimpleExecutor;
import com.wzy.mybatisXML.executor.statement.MappedStatement;
import com.wzy.mybatisXML.sqlsession.Configuration;
import com.wzy.mybatisXML.sqlsession.SqlSession;
import com.wzy.mybatisXML.utils.CommonUtils;

import java.util.List;

public class DefaultSqlSession implements SqlSession {
    
    private final Configuration configuration;
    
    private final Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.executor = new SimpleExecutor(configuration);
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }

    @Override
    public <T> T selectOne(String statementId, Object paramter) {
        List<T> results = this.selectList(statementId,paramter);
        return CommonUtils.isNotEmpty(results) ? results.get(0) : null;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object paramter) {
        MappedStatement mappedStatement = this.configuration.getMappedStatement(statementId);
        return this.executor.doQuery(mappedStatement,paramter);
    }

    @Override
    public void update(String statementId, Object paramter) {
        MappedStatement mappedStatement = this.configuration.getMappedStatement(statementId);
        this.executor.doUpdate(mappedStatement,paramter);
    }

    @Override
    public void insert(String statementId, Object paramter) {
        MappedStatement mappedStatement = this.configuration.getMappedStatement(statementId);
        this.executor.doInsert(mappedStatement,paramter);
    }
    
    public <T> T getMapper(Class<T> type){
        return configuration.getMapper(type,this);
    }
}

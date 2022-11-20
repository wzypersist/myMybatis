package com.wzy.mybatisXML.executor;

import com.wzy.mybatisXML.executor.statement.MappedStatement;

import java.util.List;

public interface Executor {
    
    <E> List<E> doQuery(MappedStatement ms,Object param);
    
    void doUpdate(MappedStatement ms,Object param);
    
    void doInsert(MappedStatement ms,Object param);
    
}

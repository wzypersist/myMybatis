package com.wzy.mybatisXML.binding;

import com.wzy.mybatisXML.executor.statement.MappedStatement;
import com.wzy.mybatisXML.sqlsession.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

public class MapperProxy<T> implements InvocationHandler {
    
    private SqlSession sqlSession;
    
    private final Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if(Object.class.equals(method.getDeclaringClass())){
                return method.invoke(this,args);
            }
            return this.execute(method,args);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private Object execute(Method method, Object[] args) {
        String statementId = this.mapperInterface.getName() + "." + method.getName();
        MappedStatement ms = this.sqlSession.getConfiguration().getMappedStatement(statementId);
        
        Object result = null;
        switch (ms.getSqlCommandType())
        {
            case SELECT:
            {
                Class<?> returnType = method.getReturnType();
                if(Collection.class.isAssignableFrom(returnType)){
                    result = sqlSession.selectList(statementId,args);
                }
                else{
                    result = sqlSession.selectOne(statementId,args);
                }
                break;
            }
            case UPDATE:
            {
                sqlSession.update(statementId,args);
                break;
            }
            case INSERT:
            {
                sqlSession.insert(statementId,args);
                break;
            }
            default:
            {
                break;
            }
        }
        return result;
    }
}

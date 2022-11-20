package com.wzy.mybatisAnnotation.sqlsession;


import com.wzy.mybatisAnnotation.handler.MyInvocationHandler;

import java.lang.reflect.Proxy;

public class SqlSession {
    
    public static <T> T getMapper(Class clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                new MyInvocationHandler(clazz));
    }
    
}

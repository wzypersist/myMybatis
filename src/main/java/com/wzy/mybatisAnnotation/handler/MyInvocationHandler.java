package com.wzy.mybatisAnnotation.handler;

import com.wzy.mybatisAnnotation.annotation.Insert;
import com.wzy.mybatisAnnotation.annotation.Param;

import com.wzy.mybatisAnnotation.annotation.Query;
import com.wzy.mybatisAnnotation.utils.JDBCUtils;
import com.wzy.mybatisAnnotation.utils.SQLUtils;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MyInvocationHandler implements InvocationHandler {
    
    private Class clazz;

    public MyInvocationHandler(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Query query = method.getDeclaredAnnotation(Query.class);
        if(query != null){
            return getResult(method,query,args);
        }
        Insert insert = method.getDeclaredAnnotation(Insert.class);
        if(insert != null){
            String insertSql = insert.value();
            //获取sql语句的参数
            String[] insertParam = SQLUtils.getInsertParams(insertSql);
            //参数绑定
            ConcurrentHashMap<String, Object> paramMap = getMethodParam(method, args);
            //将参数值加入list
            List<Object> paramValueList = addParamToList(insertParam, paramMap);
            insertSql = SQLUtils.replaceParam(insertSql,insertParam);
            return JDBCUtils.insert(insertSql,false,paramValueList);
        }
        return null;
    }

    private List<Object> addParamToList(String[] insertParam, ConcurrentHashMap<String, Object> paramMap) {
        List<Object> paramValueList = new ArrayList<>();
        for (int i = 0; i < insertParam.length; i++) {
            Object paramValue = paramMap.get(insertParam[i].trim());
            paramValueList.add(paramValue);
        }
        return paramValueList;
    }

    private Object getResult(Method method, Query query, Object[] args) throws SQLException, IllegalAccessException, InstantiationException{
        String querySql = query.value();
        //获取sql参数
        List<Object> paramList = SQLUtils.getSelectParams(querySql);
        //替换sql参数
        querySql = SQLUtils.replaceParam(querySql,paramList);
        ConcurrentHashMap<String, Object> paramMap = getMethodParam(method,args);
        List<Object> paramValueList = new ArrayList<>();
        for (Object param : paramList) {
            Object paramValue = paramMap.get(param);
            paramValueList.add(paramValue);
        }
        System.out.println("paramValueList"+ paramValueList);
        ResultSet rs = JDBCUtils.query(querySql,paramValueList);
        if(!rs.next()){return null;}
        Class<?> returnType = method.getReturnType();
        Object o = returnType.newInstance();
        rs.previous();
        while(rs.next()){
            Field[] fields = returnType.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                String fieldValue = rs.getString(fieldName);
                field.setAccessible(true);
                field.set(o,fieldValue);
            }
        }
        return o;
    }

    private ConcurrentHashMap<String, Object> getMethodParam(Method method, Object[] args) {
        ConcurrentHashMap<String , Object> paramMap = new ConcurrentHashMap<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Param param = parameters[i].getAnnotation(Param.class);
            if(param == null){continue;}
            String paramName = param.value();
            Object paramValue = args[i];
            paramMap.put(paramName,paramValue);
        }
        return paramMap;
    }
}

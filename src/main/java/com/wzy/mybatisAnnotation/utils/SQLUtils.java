package com.wzy.mybatisAnnotation.utils;

import java.util.ArrayList;
import java.util.List;

public class SQLUtils {
    
    public static List<Object> getSelectParams(String sql){
        List<Object> paramList = new ArrayList<>();
        //select username, city from user  where username = #{username} and city = #{city}
        if(!sql.contains("where")){
            return paramList;
        }
        int startIndex = sql.indexOf("where")+5;
        String whereCluse = sql.substring(startIndex);
        String[] paramStrs = whereCluse.split("and");
        
        for (String paramStr : paramStrs) {
            String sp = paramStr.split("=")[1];
            String param = sp.replace("#{","").replace("}","");
            paramList.add(param.trim());
        }
        return paramList;
        
    }
    
    public static String replaceParam(String sql,List<Object> selectParams){
        for (int i = 0; i < selectParams.size(); i++) {
            Object paramName = selectParams.get(i);
            sql = sql.replace("#{"+paramName+"}", "?");
        }
        return sql;
    }
    
    public static String replaceParam(String sql,String[] insertParams){
        for (int i = 0; i < insertParams.length; i++) {
            Object paramName = insertParams[i].trim();
            sql = sql.replace("#{"+paramName+"}", "?");
        }
        return sql;
    }

    public static String[] getInsertParams(String insertSql) {
        insertSql = insertSql.toLowerCase();
        int startIndex = insertSql.indexOf("values")+6;
        String paramValue = insertSql.substring(startIndex).replace("#{","").replace("}","").replace("(","").replace(")","");
        return paramValue.split(",");
    }
}

package com.wzy.mybatisXML.executor.parameter;

import com.wzy.mybatisXML.bean.User;
import com.wzy.mybatisXML.executor.statement.MappedStatement;
import com.wzy.mybatisXML.sqlsession.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class DefaultParameterHandler implements ParameterHandler {
    
    private Object parameter;
    
    private MappedStatement ms;

    public DefaultParameterHandler(Object parameter) {
        this.parameter = parameter;
    }

    public DefaultParameterHandler(Object parameter, MappedStatement ms) {
        this.parameter = parameter;
        this.ms = ms;
    }

   public List<String > getParamType(){
       List<String > list = new ArrayList<>(); 
       String sql = this.ms.getSql();
       int startIndex = sql.indexOf("#");
       sql = sql.substring(startIndex);
       String[] split = sql.split(",");
       for (String s : split) {
           s = s.replace("#{","").replace("}","").replace(")","");
           list.add(s);
       }
       return list;

   }
    
    
    @Override
    public void setParameters(PreparedStatement ps) {
        try {
            if(parameter != null){
                if(parameter.getClass().isArray()){
                    Object[] params = (Object[]) parameter;
                    for (int i = 0; i < params.length; i++) {
                        
                        Object value = null;
                        if(params == null) {
                            value = null;
                            ps.setObject(i+1,value);
                        }else if(params[i] instanceof String 
                        || params[i] instanceof Integer
                        || params[i] instanceof Long){
                            value = params[i];
                            ps.setObject(i+1,value);
                        }
                        else{
                            List<String > paramType = getParamType();
                            for (int j = 0; j < paramType.size(); j++) {
                                String paramName = paramType.get(j);
                                
                                Class<?> clazz = params[i].getClass();
                                Field[] fields = clazz.getDeclaredFields();
                                for (Field field : fields) {
                                    field.setAccessible(true);
                                    if(field.getName().equals(paramName)){
                                        value = field.get(params[i]);
                                        ps.setObject(j+1,value);
                                        break;
                                    }
                                }
                                
                            }
                       
                        }
                    }
                }
                
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

package com.wzy.mybatisXML.executor.resultset;

import com.wzy.mybatisXML.executor.statement.MappedStatement;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DefaultResultSetHandler implements ResultSetHandler {
    
    private final MappedStatement mappedStatement;

    public DefaultResultSetHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    @Override
    public <E> List<E> handleResultSets(ResultSet rs) {
        try {
            List<E> result = new ArrayList<>();

            if(rs == null){
                return null;
            }

            while(rs.next()){
                if(mappedStatement.getResultType() != null){
                    Class<?> entityClass = Class.forName(mappedStatement.getResultType());
                    E entity = (E) entityClass.newInstance();
                    Field[] declaredFields = entityClass.getDeclaredFields();
                    for (Field field : declaredFields) {
                        field.setAccessible(true);
                        Class<?> fieldType = field.getType();
                        if(String.class.equals(fieldType)){
                            field.set(entity,rs.getString(field.getName()));
                        }else if(int.class.equals(fieldType) || Integer.class.equals(fieldType)){
                            field.set(entity,rs.getInt(field.getName()));
                        }else{
                            field.set(entity,rs.getObject(field.getName()));
                        }
                    }
                    result.add(entity);
                }else if(mappedStatement.getResultMap() != null){

                    Class<?> entityClass = Class.forName(mappedStatement.getResultMap().getType());
                    E entity = (E) entityClass.newInstance();
                    
                    for (Field field : entityClass.getDeclaredFields()) {
                        field.setAccessible(true);
                        Class<?> fieldType = field.getType();
                        if (!mappedStatement.getColumnProperty().getProperties().contains(field.getName())) {
                            if(String.class.equals(fieldType)){
                                field.set(entity,rs.getString(field.getName()));
                            }else if(int.class.equals(fieldType) || Integer.class.equals(fieldType)){
                                field.set(entity,rs.getInt(field.getName()));
                            }else{
                                field.set(entity,rs.getObject(field.getName()));
                            }
                            
                        }else{
                            for (String property : mappedStatement.getColumnProperty().getProperties()) {
                                if(property.equals(field.getName())){
                                    if(String.class.equals(fieldType)){
                                        field.set(entity,rs.getString(mappedStatement.getColumnProperty().getColumn()));
                                    }else if(int.class.equals(fieldType) || Integer.class.equals(fieldType)){
                                        field.set(entity,rs.getInt(mappedStatement.getColumnProperty().getColumn()));
                                    }else{
                                        field.set(entity,rs.getObject(mappedStatement.getColumnProperty().getColumn()));
                                    }
                                }
                            }

                        }
                       
                    }
                    result.add(entity);
                }
               
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        
        
        return null;
    }
}

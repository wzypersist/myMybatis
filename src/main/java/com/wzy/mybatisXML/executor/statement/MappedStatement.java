package com.wzy.mybatisXML.executor.statement;

import com.wzy.mybatisXML.constants.Constant;
import com.wzy.mybatisXML.mapping.ColumnProperty;
import com.wzy.mybatisXML.mapping.ResultMap;
import lombok.Data;

import java.util.List;

/**
 * 封装Xml处理结果对象
 */
@Data
public class MappedStatement {
    
    // mapper文件的namespace
    private String namespace;
    
    // sql的id属性
    private String sqlId;
    
    // sql语句
    private String sql;
    
    //参数类型
    private String parameterType;
    
    // 返回类型
    private String resultType;
    
    private ResultMap resultMap;
    
    private ColumnProperty columnProperty;
    
    // 对于不同crud类型
    private Constant.SqlType sqlCommandType;
    
    
    
}

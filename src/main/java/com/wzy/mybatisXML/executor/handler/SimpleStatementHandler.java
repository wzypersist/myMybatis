package com.wzy.mybatisXML.executor.handler;

import com.wzy.mybatisXML.executor.statement.MappedStatement;
import com.wzy.mybatisXML.utils.CommonUtils;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleStatementHandler implements StatementHandler {
    
    private static Pattern param_pattern = Pattern.compile("#\\{([^\\{\\}]*)\\}");
    
    private MappedStatement mappedStatement;

    public SimpleStatementHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    @Override
    public PreparedStatement prepare(Connection connection) throws Exception {
        String originalSql = mappedStatement.getSql();
        if(CommonUtils.isNotEmpty(originalSql)){
            return connection.prepareStatement(parse(originalSql));
        }else {
            throw new SQLException("original sql is null");
        }
        
    }

    private String parse(String originalSql) {
        originalSql = originalSql.trim();
        Matcher matcher = param_pattern.matcher(originalSql);
        return matcher.replaceAll("?");
    }

    @Override
    public ResultSet query(PreparedStatement ps) throws Exception {
        
        return ps.executeQuery();
    }

    @Override
    public void update(PreparedStatement ps) throws Exception {
        ps.executeUpdate();
    }
}

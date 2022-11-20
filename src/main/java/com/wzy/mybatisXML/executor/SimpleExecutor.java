package com.wzy.mybatisXML.executor;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import com.wzy.mybatisXML.constants.Constant;
import com.wzy.mybatisXML.executor.handler.SimpleStatementHandler;
import com.wzy.mybatisXML.executor.parameter.DefaultParameterHandler;
import com.wzy.mybatisXML.executor.resultset.DefaultResultSetHandler;
import com.wzy.mybatisXML.executor.resultset.ResultSetHandler;
import com.wzy.mybatisXML.executor.statement.MappedStatement;
import com.wzy.mybatisXML.sqlsession.Configuration;



import java.sql.*;
import java.util.List;

public class SimpleExecutor implements Executor {
    
    private static Connection connection;
    
    private Configuration conf;

    public SimpleExecutor(Configuration conf) {
        this.conf = conf;
    }
    
    static {
        initConnect();
    }

    private static void initConnect() {
        String driver = Configuration.getProperty(Constant.DB_DRIVER);
        String url = Configuration.getProperty(Constant.DB_URL);
        String username = Configuration.getProperty(Constant.DB_USERNAME);
        String password = Configuration.getProperty(Constant.DB_PASSWORD);
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url,username,password);
            System.out.println("数据库连接成功！！！");
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws Exception{
        if(connection != null){
            return connection;
        }else{
            throw new SQLException("无法连接数据库，请检查配置");
        }
    }

    @Override
    public <E> List<E> doQuery(MappedStatement ms, Object param) {
        try {
            Connection connection = getConnection();
            
            MappedStatement mappedStatement = conf.getMappedStatement(ms.getSqlId());

            SimpleStatementHandler statementHandler = new SimpleStatementHandler(mappedStatement);

            PreparedStatement preparedStatement = statementHandler.prepare(connection);

            DefaultParameterHandler parameterHandler = new DefaultParameterHandler(param);
            parameterHandler.setParameters(preparedStatement);

            ResultSet rs = statementHandler.query(preparedStatement);
            ResultSetHandler resultSetHandler = new DefaultResultSetHandler(mappedStatement);
            return resultSetHandler.handleResultSets(rs);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void doUpdate(MappedStatement ms, Object param) {
        try {
            Connection connection = getConnection();

            MappedStatement mappedStatement = conf.getMappedStatement(ms.getSqlId());

            SimpleStatementHandler statementHandler = new SimpleStatementHandler(mappedStatement);

            PreparedStatement preparedStatement = statementHandler.prepare(connection);

            DefaultParameterHandler parameterHandler = new DefaultParameterHandler(param);
            parameterHandler.setParameters(preparedStatement);
            
            statementHandler.update(preparedStatement);
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }

    @Override
    public void doInsert(MappedStatement ms, Object param) {
        try {
            Connection connection = getConnection();

            MappedStatement mappedStatement = conf.getMappedStatement(ms.getSqlId());

            SimpleStatementHandler statementHandler = new SimpleStatementHandler(mappedStatement);

            PreparedStatement preparedStatement = statementHandler.prepare(connection);

            DefaultParameterHandler parameterHandler = new DefaultParameterHandler(param,mappedStatement);
            parameterHandler.setParameters(preparedStatement);

            statementHandler.update(preparedStatement);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

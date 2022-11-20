package com.wzy.mybatisXML.executor.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface StatementHandler {

    /**
     * sql预处理
     * @param connection
     * @return
     * @throws Exception
     */
    PreparedStatement prepare(Connection connection) throws Exception;

    /**
     * 查询
     * @param ps
     * @return
     * @throws Exception
     */
    ResultSet query(PreparedStatement ps) throws Exception;

    /**
     * 修改
     * @param ps
     * @throws Exception
     */
    void update(PreparedStatement ps) throws Exception;
    
}

package com.wzy.mybatisAnnotation.utils;

import java.sql.*;
import java.util.Date;
import java.util.List;

public final class JDBCUtils {
    
    private static String connect;
    private static String driverClassName;
    private static String URL;
    private static String username;
    private static String password;
    private static boolean autoCommit;
    
    private static Connection conn;
    
    static {
        config();
    }

    private static void config() {
        driverClassName = "com.mysql.jdbc.Driver";
        URL = "jdbc:mysql://localhost:3306/user?useUnicode=true&characterEncoding=UTF-8";
        username = "root";
        password = "1998726";
        autoCommit = false;
    }
    
    private static boolean load(){
        try {
            Class.forName(driverClassName);
            return true;
        }catch (ClassNotFoundException e){
            System.out.println("驱动类"+driverClassName+" 加载失败");
        }
        return false;
    }

    /**
     * 检查缓存的连接是否不可以被使用 ，不可以被使用的话，就返回 true
     */
    private static boolean invalid(){
        if (conn != null){
            try {
                if(conn.isClosed() || !conn.isValid(3)){
                    return true;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            return false;
        }else {
            return true;
        }
    }
    
    public static Connection connect(){
        if(invalid()){
            load();
            try {
                conn = DriverManager.getConnection(URL,username,password);
            }catch (SQLException e){
                System.out.println("建立 "+conn + " 数据库连接失败，"+e.getMessage());
            }
        }
        return conn;
    }
    
    public static void transaction(){
        try{
            conn.setAutoCommit(autoCommit);
        }catch (SQLException e){
            System.out.println("设置事务的提交方式为 : " + (autoCommit ? "自动提交" : "手动提交") + " 时失败: " + e.getMessage());
        }
    }
    
    public static Statement statement(){
        Statement statement = null;
        connect();
        transaction();
        try{
            statement = conn.createStatement();
        }catch (SQLException e){
            System.out.println("创建 Statement 对象失败: " + e.getMessage());
        }
        return statement;
    }
    
    private static PreparedStatement prepare(String SQL,boolean autoGeneratedKeys){
        PreparedStatement ps = null;
        connect();
        transaction();
        try {
            if(autoGeneratedKeys){
                ps = conn.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
            }else{
                ps = conn.prepareStatement(SQL);
            }
        }catch (SQLException e){
            System.out.println("创建 PreparedStatement 对象失败: \" + e.getMessage()");
        }
        return ps;
        
    }
    
//    public static ResultSet query(String SQL, List<Object> params){
//        if(SQL == null ||  SQL.)
//    }
    
    public static void release(Object closable){
        if(closable != null){
            if(closable instanceof ResultSet){
                ResultSet rs = (ResultSet) closable;
                try {
                    rs.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            
            if(closable instanceof Statement){
                Statement statement = (Statement) closable;
                try {
                    statement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            
            if(closable instanceof Connection){
                Connection connection = (Connection) closable;
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static ResultSet query(String sql, List<Object> params) {
        if(sql == null || sql.trim().isEmpty() || !sql.trim().toLowerCase().startsWith("select")){
            throw new RuntimeException("你的SQL语句为空或不是查询语句");
        }
        ResultSet rs = null;
        if(params != null && params.size() > 0){
            PreparedStatement ps = prepare(sql, false);
            try {
                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i+1,params.get(i));
                }
                rs = ps.executeQuery();
            }catch (SQLException e){
                System.out.println("执行SQL失败: " + e.getMessage());
            }
        }else{
            Statement statement = statement();
            try {
                rs = statement.executeQuery(sql);
            }catch (SQLException e){
                System.out.println("执行SQL失败: " + e.getMessage());
            }
        }
        return rs;
    }

    /*
     *
     * @param SQL 需要执行的 INSERT 语句
     *
     * @param autoGeneratedKeys 指示是否需要返回由数据库产生的键
     *
     * @param params 将要执行的SQL语句中包含的参数占位符的 参数值
     *
     * @return 如果指定 autoGeneratedKeys 为 true 则返回由数据库产生的键； 如果指定 autoGeneratedKeys
     * 为 false 则返回受当前SQL影响的记录数目
     */
    public static Object insert(String insertSql, boolean autoGeneratedKeys, List<Object> params) {
        int var = -1;
        if(insertSql == null || insertSql.trim().isEmpty()){
            throw new RuntimeException("你没有指定SQL语句，请检查是否指定了需要执行的SQL语句");
        }
        if(!insertSql.trim().toLowerCase().startsWith("insert")){
            throw new RuntimeException("你指定的SQL语句不是插入语句，请检查你的SQL语句");
        }
        insertSql = insertSql.trim();
        insertSql = insertSql.toLowerCase();
        if(params != null && params.size() > 0){
            PreparedStatement ps = prepare(insertSql, autoGeneratedKeys);
            Connection c = null;
            try{
                c = ps.getConnection();
            }catch (SQLException e){
                e.printStackTrace();
            }
            try {
                for (int i = 0; i < params.size(); i++) {
                    Object p = params.get(i);
                    p = typeOf(p);
                    ps.setObject(i+1,p);
                }
                int count = ps.executeUpdate();
                if(autoGeneratedKeys){
                    ResultSet rs = ps.getGeneratedKeys();
                    if(rs.next()){
                        var = rs.getInt(1);
                    }
                }else{
                    var = count;
                }
                commit(c);
            }catch (SQLException e){
                System.out.println("数据保存失败: " + e.getMessage());
                rollback(c);
            }
        }
        else{
            Statement statement = statement();
            Connection c = null;
            try {
                c = statement.getConnection();
            }catch (SQLException e){
                e.printStackTrace();
            }
            try {
                statement.executeUpdate(insertSql);
                commit(c);
                //r = true;
            }catch (SQLException e){
                System.out.println( " 失败: " + e.getMessage());
                rollback(c); // 回滚事务
            }
        }
        return var;
    }

    private static void commit(Connection c) {
        if(c != null && !autoCommit){
            try {
                c.commit();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    private static void rollback(Connection c) {
        if(c != null && !autoCommit){
            try{
                c.rollback();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    private static Object typeOf(Object o) {
        
        if(o instanceof Date){
            return new java.sql.Date(((Date) o).getTime());
        }
        if(o instanceof Character || o.getClass() == char.class){
            return String.valueOf(o);
        }
        return o;
    }
}
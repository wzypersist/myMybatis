package com.wzy.mybatisXML.test;

import com.wzy.mybatisXML.bean.User;
import com.wzy.mybatisXML.mapper.UserMapper;
import com.wzy.mybatisXML.sqlsession.SqlSession;
import com.wzy.mybatisXML.sqlsession.SqlSessionFactory;
import com.wzy.mybatisXML.sqlsession.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {

    @Autowired
    private UserService userService;
    
    public static void main(String[] args) {
//        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        UserService userService = (UserService) context.getBean("userService");
//        UserController userController = new UserController();
//        userController.test();

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");
        SqlSession session = factory.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);

        User user = userMapper.getUser("1");
        System.out.println(userMapper.selectUser("1", "张三xxxx"));
        System.out.println("******* " + user);
        System.out.println("*******all " + userMapper.getAll());

        userMapper.updateUser("1");
        System.out.println("*******all " + userMapper.getAll());
//
//        User u = new User();
//        u.setId("199");
//        u.setName("test");
        //new org.apache.ibatis.session.SqlSessionFactoryBuilder().build("conf.properties");
       // userMapper.insert(u);
    }
}

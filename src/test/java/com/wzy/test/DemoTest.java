package com.wzy.test;

import com.wzy.mybatisXML.test.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoTest extends BaseJunit4Test{
    @Autowired
    private UserService userService;
    
    @Test
    public void test(){
        userService.test();
    }
}

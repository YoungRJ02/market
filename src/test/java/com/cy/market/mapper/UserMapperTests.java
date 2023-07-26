package com.cy.market.mapper;

import com.cy.market.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTests {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void insert(){
        User user = new User();
        user.setUsername("t01");
        user.setPassword("123");
        Integer insert = userMapper.insert(user);
        System.out.println(insert);
    }

    @Test
    public void find(){
        User aa = userMapper.findByUsername("aa");
        System.out.println(aa);
    }

    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(
                10,
                "123456",
                "管理员",
                new Date());
    }

    @Test
    public void findByUid(){
        System.out.println(userMapper.findByUid(10));
    }

}

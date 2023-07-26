package com.cy.market.service;

import com.cy.market.entity.User;
import com.cy.market.mapper.UserMapper;
import com.cy.market.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {
    @Autowired
    private IUserService userService;
    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("t02");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("ok");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login(){
        User login = userService.login("young", "yang123123");
        System.out.println(login);
    }

    @Test
    public void changePassword() {
        userService.changePassword(12,"root","123","654321");
    }

}

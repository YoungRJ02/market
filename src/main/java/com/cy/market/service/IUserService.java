package com.cy.market.service;

import com.cy.market.entity.User;

public interface IUserService {
    //注册
    void reg(User user);
    //登陆功能
    User login(String username, String password);
    //修改密码
    void changePassword(Integer uid, String username, String oldpassword, String newpassword);
    //个人资料
    void changeInfo(Integer uid, String username, User user);
    User getByUid(Integer uid);
    //修改用户头像
    void changeAvatar(Integer uid, String avatar, String username);
}

package com.cy.market.service.impl;

import com.cy.market.entity.User;
import com.cy.market.mapper.UserMapper;
import com.cy.market.service.IUserService;
import com.cy.market.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        User result = userMapper.findByUsername(user.getUsername());
        if (result != null){
            throw new UsernameDuplicatedException("用户名被占用");
        }

        //md5加密
        String oldpassword = user.getPassword();
        String salt = UUID.randomUUID().toString().toUpperCase();
        user.setSalt(salt);
        String md5Password = getMd5Password(oldpassword, salt);
        user.setPassword(md5Password);

        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);
        Integer rows = userMapper.insert(user);
        if(rows != 1){
            throw new InsertException("注册过程中产生了未知错误");
        }
    }

    @Override
    public User login(String username, String password) {
        User byUsername = userMapper.findByUsername(username);
        if(byUsername == null){
            throw new UserNotFoundException("用户数据不存在");
        }
        String oldpassword = byUsername.getPassword();
        String salt = byUsername.getSalt();
        String md5Password = getMd5Password(password, salt);
        if(!md5Password.equals(oldpassword)){
            throw new PasswordNotMatchException("用户密码错误");
        }
        Integer isDelete = byUsername.getIsDelete();
        if(byUsername.getIsDelete() != null && byUsername.getIsDelete() == 1){
            throw new UserNotFoundException("用户不存在");
        }
        User user = new User();
        user.setUid(byUsername.getUid());
        user.setUsername(byUsername.getUsername());
        user.setAvatar(byUsername.getAvatar());
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldpassword, String newpassword) {
        User result = userMapper.findByUid(uid);
        if (result ==null) {
            throw new UserNotFoundException("用户数据不存在");
        }
        if(result.getIsDelete() != null && result.getIsDelete() == 1){
            throw new UserNotFoundException("用户不存在");
        }
        String oldmd5Password = getMd5Password(oldpassword, result.getSalt());
        if(!result.getPassword().equals(oldmd5Password)){
            throw new PasswordNotMatchException("密码错误");
        }

        String newmd5Password = getMd5Password(newpassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newmd5Password, username, new Date());
        if (rows != 1){
            throw new UpdateException("更新数据时产生异常");
        }
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if (result ==null) {
            throw new UserNotFoundException("用户数据不存在");
        }
        if(result.getIsDelete() != null && result.getIsDelete() == 1){
            throw new UserNotFoundException("用户不存在");
        }
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        if(rows != 1){
            throw new UpdateException("更新时产生未知错误");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User byUid = userMapper.findByUid(uid);
        if (byUid == null){
            throw new UserNotFoundException("用户不存在");
        }
        if(byUid.getIsDelete() != null && byUid.getIsDelete() == 1){
            throw new UserNotFoundException("用户不存在");
        }

        User user = new User();
        user.setUsername(byUid.getUsername());
        user.setPhone(byUid.getPhone());
        user.setEmail(byUid.getEmail());
        user.setGender(byUid.getGender());

        return user;
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        User result = userMapper.findByUid(uid);
        if (result ==null) {
            throw new UserNotFoundException("用户数据不存在");
        }
        if(result.getIsDelete() != null && result.getIsDelete() == 1){
            throw new UserNotFoundException("用户不存在");
        }
        Integer integer = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if (integer != 1){
            throw new UpdateException("更新头像时发生未知错误");
        }
    }


    /**
     * 定义加密算法处理
     * @param password
     * @param salt
     * @return
     */
    private String getMd5Password(String password, String salt){
        for(int i = 0; i < 3; i++){
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}

package com.cy.market.controller;

import com.cy.market.controller.ex.*;
import com.cy.market.service.ex.*;
import com.cy.market.util.JsonResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 控制层类的基类
 */
public class BaseController {
    public static final int OK = 200;
    @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if(e instanceof UsernameDuplicatedException){
            result.setState(4000);
            result.setMessage("用户名被占用");
        }else if (e instanceof UserNotFoundException){
            result.setState(4001);
            result.setMessage("用户数据不存在");
        }else if (e instanceof PasswordNotMatchException){
            result.setState(4002);
            result.setMessage("密码错误");
        }else if (e instanceof AddressCountLimitException) {
            result.setState(4003);
            result.setMessage("用户的收货地址超出上限的异常");
        }else if (e instanceof AddressNotFoundException) {
            result.setState(4004);
            result.setMessage("用户的收货地址数据不存在的异常");
        } else if (e instanceof AccessDeniedException) {
            result.setState(4005);
            result.setMessage("收货地址数据非法访问的异常");
        }else if (e instanceof ProductNotFoundException) {
            result.setState(4006);
            result.setMessage("访问的商品数据不存在的异常");
        }else if (e instanceof CartNotFoundException) {
            result.setState(4007);
            result.setMessage("购物车表不存在该商品的异常");
        } else if (e instanceof InsertException){
            result.setState(5000);
            result.setMessage("注册时产生未知错误");
        }else if (e instanceof DeleteException) {
            result.setState(5002);
            result.setMessage("删除数据时产生未知的异常");
        }else if (e instanceof UpdateException){
            result.setState(5003);
            result.setMessage("更新时产生未知错误");
        }else if (e instanceof FileEmptyException) {
            result.setState(6000);
            result.setMessage("文件为空");
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
            result.setMessage("文件过大");
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
            result.setMessage("文件格式错误");
        } else if (e instanceof FileStateException) {
            result.setState(6003);
            result.setMessage("文件状态错误");
        } else if (e instanceof FileUploadException) {
            result.setState(6004);
            result.setMessage("文件下载错误");
        }
        return result;
    }

    /**
     * 获取session对象中的uid
     * @param session
     * @return 当前用户uid
     */
    public final Integer getuidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取当前登录用户的username
     * @param session
     * @return
     */
    public final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}

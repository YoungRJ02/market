package com.cy.market.controller;

import com.cy.market.controller.ex.FileEmptyException;
import com.cy.market.controller.ex.FileSizeException;
import com.cy.market.controller.ex.FileStateException;
import com.cy.market.controller.ex.FileTypeException;
import com.cy.market.entity.User;
import com.cy.market.service.IUserService;
import com.cy.market.util.JsonResult;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.impl.FileUploadIOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/reg")
    public JsonResult<Void> reg(User user){
        userService.reg(user);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        User login = userService.login(username, password);
        session.setAttribute("uid", login.getUid());
        session.setAttribute("username", login.getUsername());
        System.out.println(getuidFromSession(session));
        System.out.println(getUsernameFromSession(session));
        return new JsonResult<User>(OK, login);
    }

    @RequestMapping("/change_password")
    public JsonResult<Void> changePassword(String oldPassword,
                                           String newPassword,
                                           HttpSession session) {
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid, username, oldPassword, newPassword);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        User user = userService.getByUid(getuidFromSession(session));
        return new JsonResult<>(OK, user);
    }

    @RequestMapping("/change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session){
        userService.changeInfo(getuidFromSession(session), getUsernameFromSession(session), user);
        return new JsonResult<>(OK);
    }

    /*上传文件最大值*/
    public static final int AVATAR_MAX_SIZE = 10 *1024 *1024;
    /*上传文件类型*/
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpg");
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }
    @RequestMapping("/change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session, MultipartFile file) throws IOException {
        if(file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }
        if (file.getSize() > AVATAR_MAX_SIZE){
            throw new FileSizeException("文件超出限制");
        }
        String contentType = file.getContentType();
        if(!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("文件类型不支持");
        }
        String parent = session.getServletContext().getRealPath("upload");
        File dir = new File(parent);
        if(!dir.exists()){
            dir.mkdirs();
        }
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;
        File dest = new File(dir, filename);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new IOException(e);
        } catch (FileStateException e){
            throw new FileStateException("文件状态异常");
        }
        String avatar = "/upload/" + filename;
        userService.changeAvatar(getuidFromSession(session),  avatar, getUsernameFromSession(session));
        return new JsonResult<>(OK, avatar);
    }
}

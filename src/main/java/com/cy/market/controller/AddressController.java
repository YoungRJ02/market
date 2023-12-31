package com.cy.market.controller;

import com.cy.market.entity.Address;
import com.cy.market.service.IAddressService;
import com.cy.market.service.ex.InsertException;
import com.cy.market.util.JsonResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController extends BaseController{
    @Autowired
    private IAddressService addressService;

    @RequestMapping("/add_new_address")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session){
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.addNewAddress(uid, username, address);
        return new JsonResult<>(OK);
    }

    @RequestMapping({"","/"})
    public JsonResult<List<Address>> getByUid(HttpSession session) {
        Integer uid = getuidFromSession(session);
        List<Address> data = addressService.getByUid(uid);
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("/{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session){
        session.setAttribute("aid", aid);
        addressService.setDefault(aid, getuidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }

    @RequestMapping("/updateAddress")
    public JsonResult<Void> updateOneAddress(Address address,HttpSession session){
        Integer aid = (Integer) session.getAttribute("aid");
        Integer uid = getuidFromSession(session);
        String modifiedUser = getUsernameFromSession(session);
        int result = addressService.updateOneAddress(address, modifiedUser);
        if (result == 0){
            throw new InsertException("修改地址时，服务器或数据库异常");
        }
        return new JsonResult<>(OK);
    }

    @RequestMapping("/{aid}/delete")
    public JsonResult<Void> delete(@PathVariable("aid") Integer aid, HttpSession session){
        addressService.delete(aid, getuidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }
}

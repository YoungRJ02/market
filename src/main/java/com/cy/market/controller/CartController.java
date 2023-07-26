package com.cy.market.controller;

import com.cy.market.service.ICartService;
import com.cy.market.service.ex.DeleteException;
import com.cy.market.util.JsonResult;
import com.cy.market.vo.CartVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController extends BaseController{
    @Autowired
    private ICartService cartService;

    @RequestMapping("/add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer amount, HttpSession session){
        cartService.addToCart(getuidFromSession(session), pid, amount, getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }

    @RequestMapping({"", "/"})
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
        List<CartVO> data = cartService.getVOByUid(getuidFromSession(session));
        return new JsonResult<List<CartVO>>(OK, data);
    }

    @RequestMapping("/{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer data = cartService.addNum(
                cid,
                getuidFromSession(session),
                getUsernameFromSession(session));
        return new JsonResult<Integer>(OK, data);
    }

    @RequestMapping("/{cid}/num/reduce")
    public JsonResult<Integer> reduceNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer data = cartService.reduceNum(
                cid,
                getuidFromSession(session),
                getUsernameFromSession(session));
        return new JsonResult<Integer>(OK, data);
    }

    //处理根据cids内的指定cid删除cart的请求
    @RequestMapping("/{cid}/deleteCart")
    public JsonResult<Void> deleteCartByCid(@PathVariable("cid") Integer cid){
        Integer integer = cartService.deleteCartByCid(cid);
        if (integer == 0){
            throw new DeleteException("删除时出现未知错误");
        }
        return new JsonResult<>(OK);
    }

    @RequestMapping("/list")
    public JsonResult<List<CartVO>> findVOByCids(Integer[] cids, HttpSession session){
        List<CartVO> list = cartService.getVOByCids(getuidFromSession(session), cids);
        return new JsonResult<>(OK, list);
    }
}

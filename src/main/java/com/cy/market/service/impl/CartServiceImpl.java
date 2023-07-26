package com.cy.market.service.impl;

import com.cy.market.entity.Cart;
import com.cy.market.entity.Product;
import com.cy.market.mapper.CartMapper;
import com.cy.market.mapper.ProductMapper;
import com.cy.market.service.ICartService;
import com.cy.market.service.ex.*;
import com.cy.market.util.JsonResult;
import com.cy.market.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.ProviderNotFoundException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        Cart result = cartMapper.findByUidAndPid(pid, uid);
        if (result == null){
            Cart cart = new Cart();
            cart.setPid(pid);
            cart.setUid(uid);
            cart.setNum(amount);
            Product product = productMapper.findById(pid);
            Long price = product.getPrice();
            cart.setPrice(price);

            cart.setCreatedUser(username);
            cart.setCreatedTime(new Date());
            cart.setModifiedUser(username);
            cart.setModifiedTime(new Date());

            Integer rows = cartMapper.insert(cart);
            if (rows != 1){
                throw new InsertException("插入商品时出现未知错误");
            }
        }else {
            Integer num = result.getNum();
            Integer rows = cartMapper.updateNumByCid(result.getCid(), num + amount, username, new Date());
            if (rows != 1) {
                throw new InsertException("更新数据时产生未知异常");
            }
        }

    }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        List<CartVO> list = cartMapper.findVOByUid(uid);
        return list;
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart cart = cartMapper.findByCid(cid);
        if (cart == null){
            throw new CartNotFoundException("无此条商品记录");
        }
        if (!cart.getUid().equals(uid)){
            throw new AccessDeniedException("数据非法访问");
        }
        Integer num = cart.getNum() + 1;
        Integer rows = cartMapper.updateNumByCid(cid, num, username, new Date());
        if (rows != 1){
            throw new UpdateException("更新时发生未知错误");
        }
        return num;
    }

    @Override
    public Integer reduceNum(Integer cid, Integer uid, String username) {
        Cart cart = cartMapper.findByCid(cid);
        if (cart == null){
            throw new CartNotFoundException("无此条商品记录");
        }
        if (!cart.getUid().equals(uid)){
            throw new AccessDeniedException("数据非法访问");
        }
        Integer num = cart.getNum() - 1;
        if (num == 0){
            cartMapper.deleteCartByCid(cid);
        }else {
            Integer rows = cartMapper.updateNumByCid(cid, num, username, new Date());
            if (rows != 1){
                throw new UpdateException("更新时发生未知错误");
            }
        }
        return num;
    }

    @Override
    public Integer deleteCartByCid(Integer cid) {
        int rows = cartMapper.deleteCartByCid(cid);
        if (rows == 0){
            throw new DeleteException("删除时发生未知错误");
        }
        return rows;
    }

    @Override
    public List<CartVO> getVOByCids(Integer uid, Integer[] cids) {
        List<CartVO> list = cartMapper.findVOByCids(cids);
        Iterator<CartVO> it = list.iterator();
        while (it.hasNext()) {
            CartVO cart = it.next();
            if (!cart.getUid().equals(uid)) {
                it.remove();
            }
        }
        return list;
    }
}

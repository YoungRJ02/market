package com.cy.market.service;

import com.cy.market.vo.CartVO;

import java.util.List;

public interface ICartService {
    //添加商品到购物车
    void addToCart(Integer uid, Integer pid, Integer amount, String username);
    List<CartVO> getVOByUid(Integer uid);
    Integer addNum(Integer cid,Integer uid, String username);
    Integer reduceNum(Integer cid,Integer uid, String username);
    Integer deleteCartByCid(Integer cid);
    List<CartVO> getVOByCids(Integer uid, Integer[] cids);
}

package com.cy.market.mapper;

import com.cy.market.entity.Cart;
import com.cy.market.vo.CartVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface CartMapper {
    Integer insert(Cart cart);
    Integer updateNumByCid(Integer cid, Integer num, String modifiedUser, Date modifiedTime);
    Cart findByUidAndPid(Integer pid, Integer uid);
    List<CartVO> findVOByUid(Integer uid);
    Cart findByCid(Integer cid);
    int deleteCartByCid(Integer cid);
    List<CartVO> findVOByCids(Integer[] cids);
}


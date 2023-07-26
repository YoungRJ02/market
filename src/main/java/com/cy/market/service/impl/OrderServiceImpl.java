package com.cy.market.service.impl;

import com.cy.market.entity.Address;
import com.cy.market.entity.Order;
import com.cy.market.entity.OrderItem;
import com.cy.market.mapper.OrderMapper;
import com.cy.market.service.IAddressService;
import com.cy.market.service.ICartService;
import com.cy.market.service.IOrderService;
import com.cy.market.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private IAddressService addressService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ICartService cartService;

    @Override
    public Order create(Integer aid, Integer[] cids, Integer uid, String username) {
        List<CartVO> list = cartService.getVOByCids(uid, cids);
        Long totalPrice = 0L;
        for (CartVO cartVO : list){
            totalPrice += cartVO.getPrice() * cartVO.getNum();
        }
        Address address = addressService.getByAid(aid, uid);
        Order order = new Order();

        order.setUid(uid);
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        order.setOrderTime(new Date());
        order.setStatus(0);
        order.setTotalPrice(totalPrice);
        order.setCreatedUser(username);
        order.setCreatedTime(new Date());
        order.setModifiedUser(username);
        order.setModifiedTime(new Date());

        orderMapper.insertOrder(order);

        for (CartVO cartVO : list){
            OrderItem orderItem = new OrderItem();
            orderItem.setOid(order.getOid());
            orderItem.setPid(cartVO.getPid());
            orderItem.setTitle(cartVO.getTitle());
            orderItem.setImage(cartVO.getImage());
            orderItem.setPrice(cartVO.getRealPrice());
            orderItem.setNum(cartVO.getNum());
            orderItem.setCreatedUser(username);
            orderItem.setCreatedTime(new Date());
            orderItem.setModifiedUser(username);
            orderItem.setModifiedTime(new Date());
            orderMapper.insertOrderItem(orderItem);
        }


        return order;
    }
}

package com.cy.market.service;

import com.cy.market.entity.Order;

public interface IOrderService {
    Order create(Integer aid, Integer[] cids, Integer uid, String username);
}

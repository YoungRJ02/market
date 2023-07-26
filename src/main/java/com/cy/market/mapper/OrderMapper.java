package com.cy.market.mapper;

import com.cy.market.entity.Order;
import com.cy.market.entity.OrderItem;

public interface OrderMapper {
    Integer insertOrder(Order order);
    Integer insertOrderItem(OrderItem orderItem);
}

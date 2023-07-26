package com.cy.market.controller;

import com.cy.market.entity.Order;
import com.cy.market.service.IOrderService;
import com.cy.market.util.JsonResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderController extends BaseController {
    @Autowired
    private IOrderService orderService;

    @RequestMapping("create")
    public JsonResult<Order> create(Integer aid, Integer[] cids, HttpSession session) {
        Order data = orderService.create(
                aid,
                cids,
                getuidFromSession(session),
                getUsernameFromSession(session));
        return new JsonResult<>(OK,data);
    }
}

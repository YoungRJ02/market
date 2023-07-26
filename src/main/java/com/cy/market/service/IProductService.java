package com.cy.market.service;

import com.cy.market.entity.Product;

import java.util.List;

public interface IProductService {
    //查询热销商品前四名
    List<Product> findHotList();
    //根据id查商品
    Product findById(Integer id);
}

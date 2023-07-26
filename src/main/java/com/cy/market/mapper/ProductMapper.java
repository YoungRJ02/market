package com.cy.market.mapper;

import com.cy.market.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findHotList();
    Product findById(Integer id);
}

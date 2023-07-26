package com.cy.market.service.impl;

import com.cy.market.entity.Product;
import com.cy.market.mapper.ProductMapper;
import com.cy.market.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.ProviderNotFoundException;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<Product> findHotList() {
        List<Product> hotList = productMapper.findHotList();
        for(Product product : hotList){
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return hotList;
    }

    @Override
    public Product findById(Integer id) {
        Product product = productMapper.findById(id);
        if (product == null){
            throw new ProviderNotFoundException("商品不存在");
        }
        product.setPriority(null);
        product.setCreatedUser(null);
        product.setCreatedTime(null);
        product.setModifiedUser(null);
        product.setModifiedTime(null);

        return product;
    }
}

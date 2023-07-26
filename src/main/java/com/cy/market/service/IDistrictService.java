package com.cy.market.service;

import com.cy.market.entity.District;

import java.util.List;

public interface IDistrictService {
    //根据父代号查询区域信息
    List<District> getByParent(String parent);
    //根据代号查名字
    String getNameByCode(String code);
}

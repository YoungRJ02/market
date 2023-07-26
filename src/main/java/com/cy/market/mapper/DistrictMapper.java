package com.cy.market.mapper;

import com.cy.market.entity.District;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface DistrictMapper {
    //根据父代号查询地区信息
    List<District> findByParent(String parent);
    //
    String findNameByCode(String code);

}

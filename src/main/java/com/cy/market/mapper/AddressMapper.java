package com.cy.market.mapper;

import com.cy.market.entity.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface AddressMapper {
    //插入地址
    Integer insert(Address address);
    //根据用户id统计地址数量
    Integer countByUid(Integer uid);
    List<Address> findByUid(Integer uid);
    Address findByAid(Integer aid);
    Integer updateNonDefault(Integer uid);
    Integer updateDefaultByAid(Integer aid, String modifiedUser, Date modifiedTime);
    Integer updateUserAddressByAid(Address address);
    Integer deleteByAid(Integer aid);
    Address findLastModified(Integer uid);
}

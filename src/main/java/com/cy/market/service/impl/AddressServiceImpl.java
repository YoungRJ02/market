package com.cy.market.service.impl;

import com.cy.market.entity.Address;
import com.cy.market.mapper.AddressMapper;
import com.cy.market.service.IAddressService;
import com.cy.market.service.IDistrictService;
import com.cy.market.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        Integer num = addressMapper.countByUid(uid);
        if(num > maxCount){
            throw new AddressCountLimitException("地址数量达到上限");
        }

        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        address.setUid(uid);
        Integer isDefault = num == 0 ? 1 : 0;
        address.setIsDefault(isDefault);
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());
        Integer insert = addressMapper.insert(address);
        if (insert != 1){
            throw new InsertException("插入收货地址时出现未知错误");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        for (Address address : list) {
            address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setIsDefault(null);
            address.setCreatedTime(null);
            address.setCreatedUser(null);
            address.setModifiedTime(null);
            address.setModifiedUser(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        Address address = addressMapper.findByAid(aid);
        if (address == null){
            throw new AddressNotFoundException("收货地址不存在");
        }
        if (!address.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问");
        }
        Integer integer = addressMapper.updateNonDefault(uid);
        if (integer < 1){
            throw new UpdateException("更新数据时产生未知的异常");
        }
        Integer integer1 = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (integer1 < 1){
            throw new UpdateException("更新数据时产生未知的异常");
        }
    }

    @Override
    public Integer updateOneAddress(Address address, String modifiedUser) {
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);
        address.setModifiedUser(modifiedUser);
        address.setModifiedTime(new Date());
        Integer result = addressMapper.updateUserAddressByAid(address);

        return result;
    }

    @Override
    public void delete(Integer aid, Integer uid, String username) {
        Address address = addressMapper.findByAid(aid);
        if (address == null){
            throw new AddressNotFoundException("地主不存在");
        }
        if (!address.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问");
        }
        Integer rows = addressMapper.deleteByAid(aid);
        if (rows != 1){
            throw new DeleteException("删除时产生未知错误");
        }
        Integer count = addressMapper.countByUid(uid);
        if (count == 0){
            return;
        }
        if (address.getIsDefault() != null && address.getIsDefault() == 1){
            Address lastModified = addressMapper.findLastModified(uid);
            Integer integer = addressMapper.updateDefaultByAid(lastModified.getAid(), username, new Date());
            if (integer != 1){
                throw new UpdateException("更新时产生错误");
            }
        }
    }

    @Override
    public Address getByAid(Integer aid, Integer uid) {

        Address address = addressMapper.findByAid(aid);

        if (address == null) {
            throw new AddressNotFoundException("收货地址数据不存在的异常");
        }
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);
        return address;
    }

}

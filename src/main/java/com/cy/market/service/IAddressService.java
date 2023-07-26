package com.cy.market.service;

import com.cy.market.entity.Address;

import java.util.List;

public interface IAddressService {
    void addNewAddress(Integer uid, String username, Address address);
    List<Address> getByUid(Integer uid);
    void setDefault(Integer aid,Integer uid,String username);
    Integer updateOneAddress(Address address, String modifiedUser);
    void delete(Integer aid,Integer uid,String username);
    Address getByAid(Integer aid, Integer uid);

}

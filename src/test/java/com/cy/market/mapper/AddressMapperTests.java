package com.cy.market.mapper;

import com.cy.market.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTests {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(12);
        address.setPhone("133336");
        address.setName("女朋友2");
        addressMapper.insert(address);
    }

    @Test
    public void countByUid() {
        Integer count = addressMapper.countByUid(12);
        System.out.println(count);
    }

    @Test
    public void findByUid () {
        List<Address> list = addressMapper.findByUid(11);
        System.out.println(list);
    }

    @Test
    public void findByAid() {
        System.err.println(addressMapper.findByAid(5));
    }

    @Test
    public void updateNonDefault() {
        System.out.println(addressMapper.updateNonDefault(8));//有几条数据影响行数就输出几
    }

    @Test
    public void updateDefaultByAid() {
        addressMapper.updateDefaultByAid(4,"明明",new Date());
    }

    @Test
    public void deleteByAid() {
        addressMapper.deleteByAid(6);
    }

    @Test
    public void findLastModified() {
        System.out.println(addressMapper.findLastModified(8));
    }
}


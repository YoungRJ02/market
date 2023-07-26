package com.cy.market.entity;

import lombok.Data;

import java.io.Serializable;

/**省市区的数据实体类*/
@Data
public class District implements Serializable {
    private Integer id;
    private String parent;
    private String code;
    private String name;

    public District() {
    }
}


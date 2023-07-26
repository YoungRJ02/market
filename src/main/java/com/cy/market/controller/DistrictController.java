package com.cy.market.controller;

import com.cy.market.entity.District;
import com.cy.market.service.IDistrictService;
import com.cy.market.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/districts")
public class DistrictController extends BaseController{
    @Autowired
    private IDistrictService districtService;

    @RequestMapping
    public JsonResult<List<District>> getByParent(String parent){
        List<District> data = districtService.getByParent(parent);
        return new JsonResult<>(OK, data);
    }
}

package com.zzy.malladmin.controller;

import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.mbg.model.UmsResource;
import com.zzy.malladmin.mbg.model.UmsRole;
import com.zzy.malladmin.service.TestUmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-12-31
 */
@Api(tags = "TestController")
@Tag(name = "TestController", description = "测试")
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestUmsService testUmsService;



    @ApiOperation(value = "测试resourceList")
    @RequestMapping(value = "/adminResource",method = RequestMethod.GET)
    public CommonResult adminResource() {
        List<UmsResource> umsResources = testUmsService.testUmsResource(3L);
        return CommonResult.success(umsResources);
     //   List<UmsRole> umsRoles = testUmsService.testRole(1L);
       // return CommonResult.success(umsRoles);
    }

}
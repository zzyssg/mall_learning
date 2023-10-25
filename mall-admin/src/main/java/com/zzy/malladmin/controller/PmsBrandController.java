package com.zzy.malladmin.controller;

import com.zzy.malladmin.common.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName PmsController
 * @Author ZZy
 * @Date 2023/10/19 23:25
 * @Description
 * @Version 1.0
 */
@Api(tags = "PmsBrandController")
@Tag(name = "PmsBrandController",description = "品牌控制器")
@RestController
@RequestMapping("/brand")
public class PmsBrandController {

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation("分页展示所有品牌")
//    @PreAuthorize("hasAuthority('brand:list')")
    public CommonResult list() {
        return CommonResult.success();
    }


    @RequestMapping(value = "/listAll",method = RequestMethod.GET)
    @ApiOperation("展示所有品牌")
//    @PreAuthorize("hasAuthority('brand:listAll')")
    public CommonResult listAll() {
        return CommonResult.success("list all");
    }




}

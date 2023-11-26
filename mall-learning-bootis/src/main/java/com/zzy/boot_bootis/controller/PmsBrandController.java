package com.zzy.boot_bootis.controller;

import com.zzy.boot_bootis.common.api.CommonPage;
import com.zzy.boot_bootis.common.api.CommonResult;

import com.zzy.boot_bootis.mbg.model.PmsBrand;
import com.zzy.boot_bootis.service.PmsBrandService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName PmsBrandController
 * @Author ZZy
 * @Date 2023/9/3 16:57
 * @Version 1.0
 */
@Api(tags="PmsBrandController")
@Tag(name="PmsBrandController",description = "管理品牌的一些方法")
@RestController
@RequestMapping("/brand")
public class PmsBrandController {

    @Autowired
    PmsBrandService brandService;

    private static final Logger Logger = LoggerFactory.getLogger(PmsBrandController.class);

    @ApiOperation(value = "查询所有品牌")
    @RequestMapping(value="/listAll",method= RequestMethod.GET)
    @PreAuthorize("hasAuthority('brand:listAll')")
    public CommonResult<List<PmsBrand>> getBrandList() {
        List<PmsBrand> pmsBrands = brandService.listAllBrand();
        return CommonResult.success(pmsBrands);
    }

    @ApiOperation(value="创建品牌")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('brand:create')")
    public CommonResult createBrand(@RequestBody PmsBrand pmsBrand) {

        CommonResult commonResult;
        int count = brandService.createBrand(pmsBrand);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrand, "创建品牌成功");
            Logger.debug("createBrand : {}", "创建品牌成功");
        } else {
            commonResult = CommonResult.failed(" 创建品牌失败");
            Logger.debug("createBrand : {}", "创建品牌失败");
        }
        return commonResult;
    }

    @ApiOperation(value="更新品牌")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('brand:update')")
    public CommonResult update(@PathVariable("id") Long id, @RequestBody PmsBrand brand) {

        CommonResult commonResult;
        int count = brandService.updateBrand(id, brand);
        if (count == 1) {
            commonResult = CommonResult.success(brand, "更新成功");
            Logger.debug("updateBrand : {}", "更新成功");
        } else {
            commonResult = CommonResult.failed("更新失败");
            Logger.debug("updateBrand: {}", "更新失败");
        }
        return commonResult;
    }

    @ApiOperation(value = "删除品牌")
    @ApiParam(name="id",value = "根据ID删除品牌",defaultValue = "null",required = true)
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('brand:delete')")
    public CommonResult delete(@PathVariable("id") Long id) {
        CommonResult commonResult;
        int count = brandService.deleteBrand(id);
        if (count == 1) {
            Logger.debug("delete brand删除成功 id: {}", id);
            return CommonResult.success(null);
        } else {
            commonResult = CommonResult.failed("删除失败");
            Logger.debug("delete brand删除失败 : {}",id);
        }
        return commonResult;
    }

    @ApiOperation(value = "分页显示品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页数",defaultValue = "1",required = true,dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageSize",value = "每页条数",defaultValue = "3",required = true,dataTypeClass = Integer.class)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "401",description = "未登录或token已过期"),
            @ApiResponse(responseCode = "403",description = "没有相关权限")
    })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('brand:list')")
    public CommonResult listBrand(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize",defaultValue = "3") int pageSize) {
        CommonResult commonResult;
        List<PmsBrand> pmsBrands = brandService.listBrand(pageNum, pageSize);
        if (pmsBrands != null && pmsBrands.size() > 0) {
            commonResult = CommonResult.success(CommonPage.restPage(pmsBrands), "查询成功");
            Logger.debug("listBrand 第{}页数据为:{}", pageNum, pmsBrands);
        } else {
            commonResult = CommonResult.failed("未查到数据");
            Logger.debug("listBrand 第{}页无数据",pageNum);
        }
        return commonResult;
    }

    @ApiOperation(value = "根据品牌ID查询品牌")
    @RequestMapping("/getBrand/{id}")
    public CommonResult brand(@PathVariable Long id) {
        CommonResult commonResult;
        PmsBrand brand = brandService.getBrand(id);
        if (brand != null) {
            commonResult = CommonResult.success(brand, "查询成功");
            Logger.debug("gerBrand查询成功 brand:{}", brand);
        } else {
            commonResult = CommonResult.failed("未查到");
            Logger.debug("getBrand查询失败 id:{}",id);
        }
        return commonResult;
    }

}

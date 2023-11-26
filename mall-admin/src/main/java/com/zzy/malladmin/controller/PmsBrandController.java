package com.zzy.malladmin.controller;

import cn.hutool.core.collection.CollUtil;
import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.dto.PmsBrandParam;
import com.zzy.malladmin.mbg.model.PmsBrand;
import com.zzy.malladmin.service.PmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @ClassName PmsController
 * @Author ZZy
 * @Date 2023/10/19 23:25
 * @Description
 * @Version 1.0
 */
@Api(tags = "PmsBrandController")
@Tag(name = "PmsBrandController", description = "品牌控制器")
@RestController
@RequestMapping("/brand")
public class PmsBrandController {

    @Autowired
    PmsBrandService brandService;


    @ApiOperation("根据id查询品牌详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult item(@PathVariable("id") Long id) {
        PmsBrand item = brandService.getItem(id);
        if (item != null) {
            return CommonResult.success(item);
        } else {
            return CommonResult.failed();
        }

    }

    @ApiOperation("新增品牌")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody PmsBrandParam brandParam) {
        int count = brandService.create(brandParam);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("删除品牌")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public CommonResult delete(@PathVariable("id") Long id) {
        int count = brandService.delete(id);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批量删除品牌")
    @RequestMapping(value = "/delete/batch/{ids}", method = RequestMethod.GET)
    public CommonResult deleteBatch(@PathVariable("ids") List<Long> ids) {
        int count = brandService.deleteBatch(ids);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("更新品牌信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable("id") Long id,
                               @RequestBody PmsBrandParam brandParam) {
        int count = brandService.update(id, brandParam);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("分页查询品牌")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list(@RequestParam("keyword") String keyword,
                             @RequestParam("showStatus")Integer showStatus,
                             @RequestParam("pageNum") Integer pageNum,
                             @RequestParam("pageSize") Integer pageSize) {
        List<PmsBrand> list = brandService.list(keyword,showStatus, pageNum, pageSize);
        if (!CollUtil.isEmpty(list)) {
            return CommonResult.success(list);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("展示所有品牌-不分页")
    @RequestMapping(value = "/list/listAll", method = RequestMethod.GET)
    public CommonResult listAll() {
        List<PmsBrand> pmsBrands = brandService.listAll();
        if (!CollUtil.isEmpty(pmsBrands)) {
            return CommonResult.success(pmsBrands);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批量更新厂家状态")
    @RequestMapping(value = "/update/factoryStatus", method = RequestMethod.GET)
    public CommonResult updateFactoryStatus(@RequestParam("ids") List<Long> ids,
                                            @RequestParam("status") Integer status) {
        int count = brandService.updateFactoryStatus(ids, status);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("批量更新展示状态")
    @RequestMapping(value = "/update/showStatus", method = RequestMethod.GET)
    public CommonResult updateShowStatus(@RequestParam("ids") List<Long> ids,
                                         @RequestParam("status") Integer status) {
        int count = brandService.updateShowStatus(ids, status);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }
}

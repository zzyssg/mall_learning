package com.zzy.malladmin.controller;

import com.zzy.malladmin.common.CommonResult;
import com.zzy.malladmin.dto.PmsProductCategoryWithChildren;
import com.zzy.malladmin.mbg.model.PmsProductCategory;
import com.zzy.malladmin.service.PmsProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName PmsProductCategoryController
 * @Author ZZy
 * @Date 2023/11/12 19:46
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/productCategory")
@Api(tags = "PmsProductCategoryController")
@Tag(name = "PmsProductCategoryController", description = "商品类型管理")
public class PmsProductCategoryController {


    @Autowired
    PmsProductCategoryService productCategoryService;

    /**
     * 创建商品类型
     *
     * @param pmsProductCategory
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiOperation("创建商品类型")
    public CommonResult create(@RequestBody PmsProductCategory pmsProductCategory) {
        int count = productCategoryService.create(pmsProductCategory);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }

    }

    /**
     * 更新类型信息
     *
     * @param id
     * @param pmsProductCategory
     * @return
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ApiOperation("更新类型信息")
    public CommonResult update(@PathVariable("id") Long id,
                               @RequestBody PmsProductCategory pmsProductCategory) {
        int count = productCategoryService.update(id, pmsProductCategory);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }


    /**
     * 根据父类id分页查询商品类型
     *
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
    @ApiOperation("根据父级ID查询其子级类型")
    public CommonResult list(@PathVariable("parentId") Long parentId,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<PmsProductCategory> productCategoryList = productCategoryService.listByParentId(parentId, pageNum, pageSize);
        if (productCategoryList != null) {
            return CommonResult.success(productCategoryList);
        } else {
            return CommonResult.failed();
        }
    }


    /**
     * 根据categoryId查询类型详情
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id获取类型信息")
    @RequestMapping("/{id}")
    public CommonResult getInfo(@PathVariable("id") Long id) {
        PmsProductCategory productCategory = productCategoryService.getInfo(id);
        if (productCategory != null) {
            return CommonResult.success(productCategory);
        } else {
            return CommonResult.failed();
        }
    }


    /**
     * 删除商品分类
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ApiOperation("根据商品类型ID删除商品类型")
    public CommonResult delete(@PathVariable("id") Long id) {
        int count = productCategoryService.delete(id);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 修改导航栏显示状态
     *
     * @return
     */
    @ApiOperation("修改导航栏显示状态")
    @RequestMapping("/update/navStatus")
    public CommonResult updateNavStatus() {
        return CommonResult.success();
    }

    /**
     * 更新显示状态
     *
     * @return
     */
    @ApiOperation("更新显示状态")
    @RequestMapping(value = "/update/showStatus", method = RequestMethod.GET)
    public CommonResult updateShowStatus() {
        return CommonResult.success();
    }

    /**
     * 查询所有一级分类和其子分类
     *
     * @return
     */
    @ApiOperation("查询所有一级分类和其子分类")
    @RequestMapping(value = "/list/withChildren", method = RequestMethod.GET)
    public CommonResult<List<PmsProductCategoryWithChildren>> listWithChildren() {
        //level = 0 为顶级
        List<PmsProductCategoryWithChildren> productCategoryWithChildrenList = productCategoryService.listWithChildren();
        if (productCategoryWithChildrenList != null) {
            return CommonResult.success(productCategoryWithChildrenList);
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 分页展示所有分类数据
     * TODO 带有上下级的数据，查询所有的数据一般不开放给前台
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ApiOperation("查询所有类型")
    public CommonResult listAll(@RequestParam("pageNum") Long pageNum,
                                @RequestParam("pageSize") Long pageSize) {
        return CommonResult.success();
    }


}

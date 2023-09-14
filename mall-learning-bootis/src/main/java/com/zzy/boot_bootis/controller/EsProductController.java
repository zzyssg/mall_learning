package com.zzy.boot_bootis.controller;

import com.zzy.boot_bootis.common.api.CommonPage;
import com.zzy.boot_bootis.common.api.CommonResult;
import com.zzy.boot_bootis.nosql.elasticsearch.document.EsProduct;
import com.zzy.boot_bootis.service.EsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

/**
 * @ClassName EsProductController
 * @Author ZZy
 * @Date 2023/9/14 22:49
 * @Description
 * @Version 1.0
 */
@RestController
@Api(tags = "EsProductController")
@Tag(name = "EsProductController", description = "EsProduct搜索")
@RequestMapping(value = "esProduct")
public class EsProductController {

    @Autowired
    EsProductService esProductService;

    @ApiOperation(value = "导入商品")
    @RequestMapping(value = "/importAll", method = RequestMethod.GET)
    public CommonResult<Integer> importAll() {
        int count = esProductService.importAll();
        return CommonResult.success(count);
    }


    @ApiOperation(value = "根据ID删除EsProduct")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult<Object> delete(@Param("id") Long id) {
        esProductService.delete(id);
        return CommonResult.success(null);
    }


    @ApiOperation(value = "根据ID批量删除EsProducct")
    @RequestMapping(value = "/delete/batch")
    public CommonResult deleteBatch(@RequestParam("ids") Long ids) {
        esProductService.delete(ids);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "根据id创建EsProduct")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public CommonResult<EsProduct> create(@Param("id") Long id) {
        EsProduct esProduct = esProductService.create(id);
        return CommonResult.success(esProduct);
    }


    @ApiOperation(value = "简单搜索")
    @RequestMapping(value = "/simpleSearch", method = RequestMethod.GET)
    public CommonResult simpleSearch(@RequestParam("pageNum") Integer pageNum,
                                     @RequestParam("pageSize") Integer pageSize,
                                     @RequestParam("keyword") String keyword) {
        Page<EsProduct> esProducts = esProductService.search(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esProducts));
    }
}

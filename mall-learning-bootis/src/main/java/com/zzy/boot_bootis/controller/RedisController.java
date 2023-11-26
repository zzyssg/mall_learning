package com.zzy.boot_bootis.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import com.zzy.boot_bootis.common.api.CommonResult;
import com.zzy.boot_bootis.mbg.model.PmsBrand;
import com.zzy.boot_bootis.service.PmsBrandService;
import com.zzy.boot_bootis.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName RedisController
 * @Author ZZy
 * @Date 2023/9/10 20:28
 * @Description
 * @Version 1.0
 */
@Api(tags = "RedisController")
@Tag(name = "RedisController",description = "redis测试controller")
@Slf4j
@RestController
@RequestMapping(value = "/redis")
public class RedisController {

    @Autowired
    RedisService redisService;

    @Autowired
    PmsBrandService pmsBrandService;


    @ApiOperation(value = "测试一般数据的redis操作")
    @RequestMapping(value = "/commonValue/{id}", method = RequestMethod.GET)
    @ApiResponses({
            @ApiResponse(responseCode = "401", description = "暂未登录或token已经过期"),
            @ApiResponse(responseCode = "403", description = "没有相关权限"),
    })
    public CommonResult commonValue(@PathVariable("id") Long id) {
        PmsBrand pmsBrand = pmsBrandService.getBrand(id);
        log.info("id of brand is : {}",pmsBrand);
        try {
            redisService.set("common", pmsBrand);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return CommonResult.success(pmsBrand);
    }

    @ApiOperation("测试基本操作")
    @RequestMapping(value = "/simpleTest",method = RequestMethod.GET)
    public CommonResult<PmsBrand> simpleTest() {
        List<PmsBrand> pmsBrands = pmsBrandService.listBrand(1, 5);
        PmsBrand pmsBrand = pmsBrands.get(0);
        String key = "redis:simple:" + pmsBrand.getId();
        redisService.set(key, pmsBrand);
        PmsBrand brand = (PmsBrand) redisService.get(key);
        return CommonResult.success(brand);
    }

    @ApiOperation("测试Hash操作")
    @RequestMapping(value = "/hashTest",method = RequestMethod.GET)
    public CommonResult<PmsBrand> hashTest() {
        List<PmsBrand> pmsBrands = pmsBrandService.listBrand(1, 5);
        PmsBrand pmsBrand = pmsBrands.get(0);
        //key = redis:hash:id , value = Object hash 化
        String key = "redis:hash:" + pmsBrand.getId();
        Map<String, Object> value = BeanUtil.beanToMap(pmsBrand);
        redisService.hSetAll(key,value);
        //get cacheValue from redis
        Map<Object, Object> cacheValue = redisService.hGetAll(key);
        PmsBrand brand = BeanUtil.toBean(cacheValue, PmsBrand.class);
        return CommonResult.success(brand);

    }

    @ApiOperation("测试Set操作")
    @RequestMapping(value = "/setTest", method = RequestMethod.GET)
    public CommonResult<Set<Object>> setTest() {
        //设置key、value
        List<PmsBrand> pmsBrands = pmsBrandService.listBrand(1, 5);
        String key = "redis:set:all";
        Object[] value = ArrayUtil.toArray(pmsBrands, PmsBrand.class);
        redisService.sAdd(key, value);
        //取出key、value
        Long al = redisService.sRemove(key, pmsBrands.get(0));
        log.info("redis sRemove al: {}", al);
        Set<Object> cacheSet = redisService.sMembers(key);
        return CommonResult.success(cacheSet);
    }

    @ApiOperation("测试List操作")
    @RequestMapping(value = "/listTest", method = RequestMethod.GET)
    public CommonResult<List<Object>> listTest() {
        //设置key、value
        //取出key、value，返回
        List<PmsBrand> pmsBrands = pmsBrandService.listBrand(1, 5);
        String key = "redis:list:all";
        Object[] value = ArrayUtil.toArray(pmsBrands, PmsBrand.class);
        redisService.lPushAll(key, value);
        //删除集合中等于value的元素 count  > 0,正向删除第一个等于value的值
        Long aLong = redisService.lRemove(key, 1, pmsBrands.get(0));
        log.info("lRemove  along: {},brand:{}", aLong,pmsBrands.get(0));
        List<Object> cachaeList = redisService.lRange(key, 0, 3);
        return CommonResult.success(cachaeList);


    }






}

package com.zzy.boot_bootis.service;

import com.zzy.boot_bootis.common.api.CommonResult;
import com.zzy.boot_bootis.dto.OrderParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName OmsPortalOrderService
 * @Author ZZy
 * @Date 2023/9/17 23:37
 * @Description
 * @Version 1.0
 */
public interface OmsPortalOrderService {


    /**
     * 根据交易信息生成订单
     * @param orderParam
     * @return
     */
    @Transactional
    CommonResult generateOrder(OrderParam orderParam);


    /**
     * 根据ID删除订单ID
     * @param orderId
     */
    @Transactional
    void cancelOrder(Long orderId);



}

package com.zzy.boot_bootis.service.impl;

import com.zzy.boot_bootis.common.api.CommonResult;
import com.zzy.boot_bootis.component.OrderCancelSender;
import com.zzy.boot_bootis.dto.OrderParam;
import com.zzy.boot_bootis.service.OmsPortalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName OmsPortalOrderServiceImpl
 * @Author ZZy
 * @Date 2023/9/17 23:43
 * @Description
 * @Version 1.0
 */
@Service
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {

    private static Logger logger = LoggerFactory.getLogger(OmsPortalOrderServiceImpl.class);

    @Autowired
    OrderCancelSender orderCancelSender;

    @Override
    public CommonResult generateOrder(OrderParam orderParam) {
        //TODO 下单
        //下单后开启一个延时消息，用户没有付款时取消订单orderID

        sendDelayMessageCancelOrder(11L);
        return CommonResult.success("下单成功");

    }

    private void sendDelayMessageCancelOrder(long orderId) {
        long delayTime = 5 * 1000;//10S
        orderCancelSender.sendMessage(orderId, delayTime);

    }

    @Override
    public void cancelOrder(Long orderId) {
        //取消订单
        logger.info("取消订单：{}", orderId);
    }
}

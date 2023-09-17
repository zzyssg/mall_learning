package com.zzy.boot_bootis.component;

import com.zzy.boot_bootis.service.OmsPortalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName OrderCancelReceiver
 * @Author ZZy
 * @Date 2023/9/18 1:07
 * @Description
 * @Version 1.0
 */
@Component
@RabbitListener(queues ="mall.order.cancel.ttl")
public class OrderCancelReceiver {

    private static Logger logger = LoggerFactory.getLogger(OrderCancelReceiver.class);


    @Autowired
    OmsPortalOrderService orderService;

    @RabbitHandler
    public void handle(Long orderId) {
        logger.info("receive delay orderId：{}", orderId);
        orderService.cancelOrder(orderId);

    }


}

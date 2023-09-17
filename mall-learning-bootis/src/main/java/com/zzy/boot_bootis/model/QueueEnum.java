package com.zzy.boot_bootis.model;

import lombok.Data;
import lombok.Getter;

/**
 * @ClassName QueueEnum
 * @Author ZZy
 * @Date 2023/9/17 16:45
 * @Description
 * @Version 1.0
 */
@Getter
public enum QueueEnum {

    QUEUE_ORDER_CANCEL("mall.order.direct","mall.order.cancel","mall.order.cancel"),
    QUEUE_TTL_ORDER_CANCEL("mall.order.direct.ttl", "mall.order.cancel.ttl", "mall.order.cancel.ttl");


    private String exchange;

    private String name;

    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }




}

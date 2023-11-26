package com.zzy.malladmin.dto;

import com.zzy.malladmin.mbg.model.OmsOrder;
import com.zzy.malladmin.mbg.model.OmsOrderItem;
import com.zzy.malladmin.mbg.model.OmsOrderOperateHistory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @program: mall_learning
 * @description: 订单详情
 * @author: zzy
 * @create: 2023-11-26
 */
public class OmsOrderDetail extends OmsOrder {

    @Getter
    @Setter
    //订单物品详情
    private List<OmsOrderItem> orderItemList;

    @Getter
    @Setter
    //订单操作历史
    private List<OmsOrderOperateHistory> orderOperateHistoryList;

}
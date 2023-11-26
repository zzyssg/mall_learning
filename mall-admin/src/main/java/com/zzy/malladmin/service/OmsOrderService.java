package com.zzy.malladmin.service;

import com.zzy.malladmin.dto.*;
import com.zzy.malladmin.mbg.model.OmsOrder;

import java.util.List;

/**
* @author zhangzheyuan
* @description 针对表【oms_order(订单表)】的数据库操作Service
* @createDate 2023-11-26 10:20:38
*/
public interface OmsOrderService {

    int insert(OmsOrderParam omsOrderParam);

    int delete(List<Long> ids);

    int update(Long id,  OrderUpdateParam orderUpdateParam);

    List<OmsOrder> search(OrderSearchParam orderSearchParam, Integer pageNum, Integer pageSize);

    OmsOrder get(Long id);

    int updateClose(List<Long> ids, String note);

    int delivery(List<OrderDeliveryParam> orderDeliveryParamList);

    int updateMoneyInfo(OrderMoneyInfoParam orderMoneyInfoParam);

    int updateNote(Long id, String note, Integer status);

    int updateReceiverInfo(OrderReceiverInfoParam orderReceiverInfoParam);

    OmsOrderDetail detail(Long id);
}

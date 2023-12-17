package com.zzy.malladmin.service;

import com.zzy.malladmin.dto.ConfirmOrderResult;
import com.zzy.malladmin.dto.OmsOrderParam;
import com.zzy.malladmin.mbg.model.OmsOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @program: mall_learning
 * @description: 订单管理
 * @author: zzy
 * @create: 2023-12-09
 */
public interface OmsPortalOrderService {
    int cancel(Long id);

    void cancelTimeOut();

    int cancelUserDo(Long orderId);

    int confirm(Long orderId);

    int delete(Long orderId);

    OmsOrder detail(Long id);

    ConfirmOrderResult generateConfirmOrder(List<Long> cartIds);

    @Transactional
    Map<String, Object> generateOrder(OmsOrderParam omsOrderParam);

    List<OmsOrder> list(Integer pageNum, Integer pageSize, Integer status);

    Integer paySuccessCallback(Long orderId, Integer payType);
}

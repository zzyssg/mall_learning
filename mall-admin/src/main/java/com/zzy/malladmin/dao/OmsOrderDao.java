package com.zzy.malladmin.dao;

import com.zzy.malladmin.dto.OmsOrderDetail;
import com.zzy.malladmin.dto.OrderDeliveryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-11-26
 */
@Repository
public interface OmsOrderDao {

    int delivery(List<OrderDeliveryParam> list);

    OmsOrderDetail detail(Long id);
}

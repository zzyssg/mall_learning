package com.zzy.malladmin.dao;

import com.zzy.malladmin.mbg.model.OmsOrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-12-17
 */
@Repository
public interface OmsOrderItemDao {

    Integer batchInsert(List<OmsOrderItem> orderItemList);

}

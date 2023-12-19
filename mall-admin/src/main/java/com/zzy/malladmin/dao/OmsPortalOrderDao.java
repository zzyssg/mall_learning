package com.zzy.malladmin.dao;

import com.zzy.malladmin.mbg.model.OmsOrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-12-19
 */
@Repository
public interface OmsPortalOrderDao {

    Integer batchUpdate(List<OmsOrderItem> orderItemList);

    Integer releaseSkuLockStock(List<OmsOrderItem> orderItemList);
}

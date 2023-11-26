package com.zzy.malladmin.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-11-26
 */
@Repository
public interface OmsOrderOperateHistoryDao {

    int insertList(List list);

}

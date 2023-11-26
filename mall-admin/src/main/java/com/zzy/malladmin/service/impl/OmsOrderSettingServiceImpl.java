package com.zzy.malladmin.service.impl;

import com.zzy.malladmin.dto.OrderSettingParam;
import com.zzy.malladmin.mbg.mapper.OmsOrderSettingMapper;
import com.zzy.malladmin.mbg.model.OmsOrderSetting;
import com.zzy.malladmin.service.OmsOrderService;
import com.zzy.malladmin.service.OmsOrderSettingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: mall_learning
 * @description: 订单设置时限
 * @author: zzy
 * @create: 2023-11-26
 */
@Service
public class OmsOrderSettingServiceImpl implements OmsOrderSettingService {
    @Autowired
    OmsOrderSettingMapper orderSettingMapper;

    @Override
    public OmsOrderSetting getItem(Long id) {
        return orderSettingMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Long id, OrderSettingParam orderSettingParam) {
        OmsOrderSetting orderSetting = new OmsOrderSetting();
        BeanUtils.copyProperties(orderSettingParam, orderSetting);
        orderSetting.setId(id);
        int count = orderSettingMapper.updateByPrimaryKey(orderSetting);
        return count;
    }
}
package com.zzy.malladmin.service;

import com.zzy.malladmin.dto.OrderSettingParam;
import com.zzy.malladmin.mbg.model.OmsOrderSetting;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zhangzheyuan
* @description 针对表【oms_order_setting(订单设置表)】的数据库操作Service
* @createDate 2023-11-26 10:20:38
*/
public interface OmsOrderSettingService {

    OmsOrderSetting getItem(Long id);

    int update(Long id, OrderSettingParam orderSettingParam);

}

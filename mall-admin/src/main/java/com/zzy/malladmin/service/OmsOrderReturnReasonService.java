package com.zzy.malladmin.service;

import com.zzy.malladmin.dto.OmsOrderReturnReasonParam;
import com.zzy.malladmin.mbg.model.OmsOrderReturnReason;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zhangzheyuan
* @description 针对表【oms_order_return_reason(退货原因表)】的数据库操作Service
* @createDate 2023-11-26 10:20:38
*/
public interface OmsOrderReturnReasonService {

    int add(OmsOrderReturnReasonParam returnReasonParam);

    List<OmsOrderReturnReason> list(Integer pageNum, Integer pageSize);

    int delete(List<Long> ids);

    int update(Long id, OmsOrderReturnReason returnReason);

    OmsOrderReturnReason getItem(Long id);

    int updateStatus(List<Long> ids, Integer status);
}

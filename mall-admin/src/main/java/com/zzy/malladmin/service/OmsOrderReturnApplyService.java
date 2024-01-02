package com.zzy.malladmin.service;

import com.zzy.malladmin.dto.OmsOrderReturnApplyQueryParam;
import com.zzy.malladmin.dto.OmsUpdateStatusParam;
import com.zzy.malladmin.mbg.model.OmsOrderReturnApply;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author zhangzheyuan
* @description 针对表【oms_order_return_apply(订单退货申请)】的数据库操作Service
* @createDate 2023-11-26 10:20:38
*/
@Service
public interface OmsOrderReturnApplyService {

    OmsOrderReturnApply getItem(Long id);

    List<OmsOrderReturnApply> list(OmsOrderReturnApplyQueryParam orderReturnApplyQueryParam, Integer pageNum, Integer pageSize);

    int delete(List<Long> ids);

    int updateStatus(Long id, OmsUpdateStatusParam updateStatusParam);

}

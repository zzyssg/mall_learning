package com.zzy.malladmin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzy.malladmin.dao.OmsOrderReturnApplyDao;
import com.zzy.malladmin.dto.OmsOrderReturnApplyQueryParam;
import com.zzy.malladmin.dto.OmsUpdateStatusParam;
import com.zzy.malladmin.mbg.mapper.OmsOrderReturnApplyMapper;
import com.zzy.malladmin.mbg.model.OmsOrderReturnApply;
import com.zzy.malladmin.mbg.model.OmsOrderReturnApplyExample;
import com.zzy.malladmin.service.OmsOrderReturnApplyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-11-26
 */
public class OmsOrderReturnApplyServiceImpl implements OmsOrderReturnApplyService {
    @Autowired
    OmsOrderReturnApplyMapper returnApplyMapper;

    @Autowired
    OmsOrderReturnApplyDao returnApplyDao;

    @Override
    public OmsOrderReturnApply getItem(Long id) {
        return returnApplyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OmsOrderReturnApply> list(OmsOrderReturnApplyQueryParam orderReturnApplyQueryParam, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OmsOrderReturnApply> orderReturnApplyList = returnApplyDao.getList(orderReturnApplyQueryParam);
        return orderReturnApplyList;
    }

    @Override
    public int delete(List<Long> ids) {
        OmsOrderReturnApplyExample returnApplyExample = new OmsOrderReturnApplyExample();
        //只能删除状态为3：已拒绝的单子
        returnApplyExample.createCriteria().andIdIn(ids).andStatusEqualTo(3);
        int count = returnApplyMapper.deleteByExample(returnApplyExample);
        return count;
    }

    @Override
    public int updateStatus(Long id, OmsUpdateStatusParam updateStatusParam) {
        //根据status更新数据 0:待处理  1：退货中  2：已完成  3:已拒绝
        OmsOrderReturnApply orderReturnApply = new OmsOrderReturnApply();
        Integer status = updateStatusParam.getStatus();
        if (1 == status) {
            //确认退货
            orderReturnApply.setId(id);
            orderReturnApply.setCompanyAddressId(updateStatusParam.getCompanyAddressId());
            orderReturnApply.setReturnAmount(updateStatusParam.getReturnAmount());
            orderReturnApply.setHandleNote(updateStatusParam.getHandleNote());
            orderReturnApply.setHandleMan(updateStatusParam.getHandleMan());
            orderReturnApply.setReceiveNote(updateStatusParam.getReceiveNote());
            orderReturnApply.setReceiveMan(updateStatusParam.getReceiverMan());
            orderReturnApply.setHandleTime(new Date());
            orderReturnApply.setStatus(status);
        } else if (2 == status) {
            //已完成退货
            orderReturnApply.setOrderId(id);
            orderReturnApply.setHandleTime(new Date());
            orderReturnApply.setHandleMan(updateStatusParam.getHandleMan());
            orderReturnApply.setHandleNote(updateStatusParam.getHandleNote());
            orderReturnApply.setStatus(status);
        } else if (3 == status) {
            //拒绝退货
            orderReturnApply.setId(id);
            orderReturnApply.setStatus(status);
            orderReturnApply.setHandleTime(new Date());
            orderReturnApply.setHandleMan(updateStatusParam.getHandleMan());
            orderReturnApply.setHandleNote(updateStatusParam.getHandleNote());
        } else {
            return 0;
        }
        int count = returnApplyMapper.updateByPrimaryKeySelective(orderReturnApply);
        return count;
    }
}
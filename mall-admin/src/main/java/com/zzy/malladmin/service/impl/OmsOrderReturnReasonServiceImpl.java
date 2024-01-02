package com.zzy.malladmin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzy.malladmin.dto.OmsOrderReturnReasonParam;
import com.zzy.malladmin.mbg.mapper.OmsOrderReturnReasonMapper;
import com.zzy.malladmin.mbg.model.OmsOrderReturnReason;
import com.zzy.malladmin.mbg.model.OmsOrderReturnReasonExample;
import com.zzy.malladmin.service.OmsOrderReturnReasonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-11-27
 */
@Service
public class OmsOrderReturnReasonServiceImpl implements OmsOrderReturnReasonService {
    @Autowired
    OmsOrderReturnReasonMapper returnReasonMapper;

    @Override
    public int add(OmsOrderReturnReasonParam returnReasonParam) {
        OmsOrderReturnReason returnReason = new OmsOrderReturnReason();
        BeanUtils.copyProperties(returnReasonParam, returnReason);
        returnReason.setCreateTime(new Date());
        int count = returnReasonMapper.insertSelective(returnReason);
        return count;
    }

    @Override
    public List<OmsOrderReturnReason> list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        OmsOrderReturnReasonExample returnReasonExample = new OmsOrderReturnReasonExample();
        returnReasonExample.setOrderByClause("sort desc");
        return returnReasonMapper.selectByExample(returnReasonExample);
    }

    @Override
    public int delete(List<Long> ids) {
        OmsOrderReturnReasonExample returnReasonExample = new OmsOrderReturnReasonExample();
        returnReasonExample.createCriteria().andIdIn(ids);
        return returnReasonMapper.deleteByExample(returnReasonExample);
    }

    @Override
    public int update(Long id, OmsOrderReturnReason returnReason) {
        returnReason.setId(id);
        return returnReasonMapper.updateByPrimaryKeySelective(returnReason);
    }

    @Override
    public OmsOrderReturnReason getItem(Long id) {
        return returnReasonMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateStatus(List<Long> ids, Integer status) {
        OmsOrderReturnReason returnReason = new OmsOrderReturnReason();
        returnReason.setStatus(status);
        OmsOrderReturnReasonExample returnReasonExample = new OmsOrderReturnReasonExample();
        returnReasonExample.createCriteria().andIdIn(ids);
        int count = returnReasonMapper.updateByExampleSelective(returnReason, returnReasonExample);
        return count;
    }

}
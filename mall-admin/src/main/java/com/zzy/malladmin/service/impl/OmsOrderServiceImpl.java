package com.zzy.malladmin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzy.malladmin.dao.OmsOrderDao;
import com.zzy.malladmin.dao.OmsOrderOperateHistoryDao;
import com.zzy.malladmin.dto.*;
import com.zzy.malladmin.mbg.mapper.OmsOrderMapper;
import com.zzy.malladmin.mbg.mapper.OmsOrderOperateHistoryMapper;
import com.zzy.malladmin.mbg.model.OmsOrder;
import com.zzy.malladmin.mbg.model.OmsOrderExample;
import com.zzy.malladmin.mbg.model.OmsOrderOperateHistory;
import com.zzy.malladmin.service.OmsOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-11-26
 */
@Service
public class OmsOrderServiceImpl implements OmsOrderService {

    @Autowired
    OmsOrderMapper orderMapper;

    @Autowired
    OmsOrderDao orderDao;

    @Autowired
    OmsOrderOperateHistoryMapper orderOperateHistoryMapper;

    @Autowired
    OmsOrderOperateHistoryDao orderOperateHistoryDao;

    @Override
    public int insert(OmsOrderParam omsOrderParam) {
        OmsOrder record = new OmsOrder();
        BeanUtils.copyProperties(omsOrderParam, record);
        return orderMapper.insert(record);
    }

    //更新deleteStatus状态为删除：1
    @Override
    public int delete(List<Long> ids) {
        OmsOrder order = new OmsOrder();
        order.setDeleteStatus(1);
        OmsOrderExample example = new OmsOrderExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andIdIn(ids);
        int count =  orderMapper.updateByExampleSelective(order, example);
        return count;
    }

    @Override
    public int update(Long id, OrderUpdateParam orderUpdateParam) {
        OmsOrder order = new OmsOrder();
        BeanUtils.copyProperties(orderUpdateParam, order);
        order.setId(id);
        return orderMapper.updateByPrimaryKey(order);
    }

    @Override
    public List<OmsOrder> search(OrderSearchParam orderSearchParam, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        OmsOrderExample orderExample = new OmsOrderExample();
        OmsOrderExample.Criteria criteria = orderExample.createCriteria();
        if (orderSearchParam.getOrderSn() != null) {
            criteria.andOrderSnLike("%" + orderSearchParam.getOrderSn() + "%");
        }
        if (orderSearchParam.getOrderType() != null) {
            criteria.andOrderTypeEqualTo(orderSearchParam.getOrderType());
        }
        if (orderSearchParam.getCreateTime() != null) {
            //TODO 时间如何处理

            criteria.andCreateTimeEqualTo(Date.valueOf(orderSearchParam.getCreateTime()));
        }
        if (orderSearchParam.getReceiverName() != null) {
            criteria.andReceiverNameLike("%" + orderSearchParam.getReceiverName() + "%");
        }
        if (orderSearchParam.getStatus() != null) {
            criteria.andStatusEqualTo(orderSearchParam.getStatus());
        }
        if (orderSearchParam.getSourceType() != null) {
            criteria.andSourceTypeEqualTo(orderSearchParam.getSourceType());
        }
        List<OmsOrder> omsOrders = orderMapper.selectByExample(orderExample);
        return omsOrders;
    }

    @Override
    public OmsOrder get(Long id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateClose(List<Long> ids, String note) {
        OmsOrder order = new OmsOrder();
        order.setStatus(4);
        OmsOrderExample orderExample = new OmsOrderExample();
        orderExample.createCriteria().andDeleteStatusEqualTo(0).andIdIn(ids);
        //关闭
        int count = orderMapper.updateByExample(order, orderExample);
        //加入操作历史
        List<OmsOrderOperateHistory> omsOrderOperateHistoryList = ids.stream().map(id -> {
            OmsOrderOperateHistory orderOperateHistory = new OmsOrderOperateHistory();
            orderOperateHistory.setNote(note);
            orderOperateHistory.setOrderId(id);
            orderOperateHistory.setOrderStatus(4);
            orderOperateHistory.setOperateMan("后台管理员");
            orderOperateHistory.setCreateTime(new java.util.Date());
            return orderOperateHistory;
        }).collect(Collectors.toList());
        orderOperateHistoryDao.insertList(omsOrderOperateHistoryList);
        return count;
    }

    @Override
    public int delivery(List<OrderDeliveryParam> orderDeliveryParamList) {
        //发货
        int count = orderDao.delivery(orderDeliveryParamList);
        //操作历史
        List<OmsOrderOperateHistory> omsOrderOperateHistoryList = orderDeliveryParamList.stream().map(orderDeliveryParam -> {
            Long id = orderDeliveryParam.getOrderId();
            OmsOrderOperateHistory orderOperateHistory = new OmsOrderOperateHistory();
            orderOperateHistory.setNote("已发货");
            orderOperateHistory.setOrderId(id);
            //2：已发货 3：已完成 4：已关闭
            orderOperateHistory.setOrderStatus(2);
            orderOperateHistory.setOperateMan("后台管理员");
            orderOperateHistory.setCreateTime(new java.util.Date());
            return orderOperateHistory;
        }).collect(Collectors.toList());
        orderOperateHistoryDao.insertList(omsOrderOperateHistoryList);
        return count;
    }

    @Override
    public int updateMoneyInfo(OrderMoneyInfoParam orderMoneyInfoParam) {
        //更新订单中的信息
        OmsOrder order = new OmsOrder();
        order.setStatus(orderMoneyInfoParam.getStatus());
        order.setFreightAmount(orderMoneyInfoParam.getFreightAmount());
        order.setDiscountAmount(orderMoneyInfoParam.getDiscountAmount());
        order.setId(orderMoneyInfoParam.getOrderId());
        int count = orderMapper.updateByPrimaryKey(order);
        //操作历史
        Long id = orderMoneyInfoParam.getOrderId();
        OmsOrderOperateHistory orderOperateHistory = new OmsOrderOperateHistory();
        orderOperateHistory.setNote("更新发货金额信息");
        orderOperateHistory.setOrderId(id);
        //3: 4：已发货
        orderOperateHistory.setOrderStatus(orderMoneyInfoParam.getStatus());
        orderOperateHistory.setOperateMan("后台管理员");
        orderOperateHistory.setCreateTime(new java.util.Date());
        return count;
    }

    @Override
    public int updateNote(Long id, String note, Integer status) {
        //更新订单备注
        OmsOrder order = new OmsOrder();
        order.setId(id);
        order.setNote(note);
        order.setStatus(status);
        int count = orderMapper.updateByPrimaryKeySelective(order);
        //更新历史
        OmsOrderOperateHistory orderOperateHistory = new OmsOrderOperateHistory();
        orderOperateHistory.setOrderId(id);
        orderOperateHistory.setOrderStatus(status);
        orderOperateHistory.setOperateMan("后台管理员");
        orderOperateHistory.setNote(note);
        orderOperateHistory.setCreateTime(new java.util.Date());
        orderOperateHistoryMapper.insert(orderOperateHistory);
        return count;
    }

    @Override
    public int updateReceiverInfo(OrderReceiverInfoParam receiverInfoParam) {
        //更新收货人信息
        OmsOrder order = new OmsOrder();
        order.setId(receiverInfoParam.getOrderId());
        order.setStatus(receiverInfoParam.getStatus());
        order.setBillHeader(receiverInfoParam.getBillHeader());
        order.setBillContent(receiverInfoParam.getBillContent());
        order.setReceiverCity(receiverInfoParam.getReceiverCity());
        order.setReceiverName(receiverInfoParam.getReceiverName());
        order.setReceiverPhone(receiverInfoParam.getReceiverPhone());
        order.setReceiverRegion(receiverInfoParam.getReceiverRegion());
        order.setReceiverProvince(receiverInfoParam.getReceiverProvince());
        order.setReceiverCity(receiverInfoParam.getReceiverCity());
        order.setReceiverPostCode(receiverInfoParam.getReceiverPostCode());
        order.setReceiverDetailAddress(receiverInfoParam.getReceiverDetailAddress());
        order.setNote(receiverInfoParam.getNote());
        order.setModifyTime(new java.util.Date());
        int count = orderMapper.updateByPrimaryKeySelective(order);
        //更新历史
        OmsOrderOperateHistory orderOperateHistory = new OmsOrderOperateHistory();
        orderOperateHistory.setOrderId(receiverInfoParam.getOrderId());
        orderOperateHistory.setOrderStatus(receiverInfoParam.getStatus());
        orderOperateHistory.setOperateMan("后台管理员");
        orderOperateHistory.setNote(receiverInfoParam.getNote());
        orderOperateHistory.setCreateTime(new java.util.Date());
        orderOperateHistoryMapper.insert(orderOperateHistory);
        return count;
    }

    @Override
    public OmsOrderDetail detail(Long id) {
        OmsOrderDetail orderDetail = orderDao.detail(id);
        return orderDetail;
    }
}
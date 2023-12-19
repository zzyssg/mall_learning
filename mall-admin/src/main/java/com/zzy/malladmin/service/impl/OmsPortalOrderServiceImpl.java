package com.zzy.malladmin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzy.malladmin.dao.OmsOrderDao;
import com.zzy.malladmin.dao.OmsOrderItemDao;
import com.zzy.malladmin.dao.OmsPortalOrderDao;
import com.zzy.malladmin.dto.CartPromotionItem;
import com.zzy.malladmin.dto.ConfirmOrderResult;
import com.zzy.malladmin.dto.OmsOrderDetail;
import com.zzy.malladmin.dto.OmsOrderParam;
import com.zzy.malladmin.exception.Assert;
import com.zzy.malladmin.mbg.mapper.*;
import com.zzy.malladmin.mbg.model.*;
import com.zzy.malladmin.service.OmsCartItemService;
import com.zzy.malladmin.service.OmsPortalOrderService;
import com.zzy.malladmin.service.RedisService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-12-09
 */
@Service
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {


    @Value("${redis.database}")
    private String REDIS_DATABASE;

    @Value("${redis.key.orderId}")
    private String REDIS_KEY_ORDER_ID;

    @Autowired
    RedisService redisService;

    @Autowired
    OmsOrderMapper orderMapper;

    @Autowired
    OmsOrderItemMapper orderItemMapper;

    @Autowired
    OmsOrderOperateHistoryMapper orderOperateHistoryMapper;

    @Autowired
    PmsSkuStockMapper skuStockMapper;

    @Autowired
    OmsPortalOrderDao portalOrderDao;



    @Autowired
    UmsIntegrationConsumeSettingMapper integrationConsumeSettingMapper;

    @Autowired
    OmsOrderItemDao orderItemDao;

    @Autowired
    OmsOrderDao orderDao;

    @Autowired
    OmsCartItemService cartItemService;

    @Override
    public int cancel(Long id) {
        OmsOrder order = new OmsOrder();
        order.setDeleteStatus(1);
        order.setModifyTime(new Date());
        order.setStatus(5);
        order.setId(id);
        int count = orderMapper.updateByPrimaryKeySelective(order);
        return count;

    }

    //自动取消延迟订单
    @Override
    public void cancelTimeOut() {
        //TODO 获取延时订单
        Long id = 1L;
        cancel(id);
    }

    //用户手动取消订单 :1、修改订单状态 2、释放锁定的库存 3、返还积分、恢复优惠券未使用状态
    @Override
    public void cancelUserDo(Long orderId) {
        //1、修改订单状态
        OmsOrder order = new OmsOrder();
        order.setDeleteStatus(1);
        order.setStatus(4);
        order.setModifyTime(new Date());
        //TODO 获取用户信息
        Long memberId = 1L;
        OmsOrderExample orderExample = new OmsOrderExample();
        orderExample.createCriteria().andIdEqualTo(orderId).andMemberIdEqualTo(memberId).andDeleteStatusEqualTo(0);
        int count = orderMapper.updateByExampleSelective(order, orderExample);
        //2、释放锁定库存
        OmsOrderItemExample orItemExample = new OmsOrderItemExample();
        orItemExample.createCriteria().andOrderIdEqualTo(orderId);
        List<OmsOrderItem> orderItemList = orderItemMapper.selectByExample(orItemExample);
        releaseSkuLockStock(orderItemList);
        //3、返还积分 TODO memberService
        //4、TODO 恢复优惠券未使用状态

    }

    //将库存加回去
    private void releaseSkuLockStock(List<OmsOrderItem> orderItemList) {
        portalOrderDao.releaseSkuLockStock(orderItemList);
    }

    @Override
    public int confirm(Long orderId) {
        //修改订单状态为：确认
        OmsOrder order = new OmsOrder();
        order.setStatus(3);
        order.setId(orderId);
        order.setModifyTime(new Date());
        //TODO 每一个动作对应的关键字段：收货时间
        order.setReceiveTime(new Date());
        int count = orderMapper.updateByPrimaryKeySelective(order);
        return count;
    }

    @Override
    public int delete(Long orderId) {
        //有些订单不能删除：别人的、订单状态不为 status=3或者4
        OmsOrder order = orderMapper.selectByPrimaryKey(orderId);
        if (order.getStatus() == 3 || order.getStatus() == 4) {
            OmsOrder deleteOrder = new OmsOrder();
            deleteOrder.setDeleteStatus(1);
            deleteOrder.setId(orderId);
            return orderMapper.updateByPrimaryKeySelective(deleteOrder);
        } else {
            Assert.fail("只能删除已完成或者无效的订单");
        }
        return -1;
    }

    @Override
    public OmsOrderDetail detail(Long id) {
        //OmsOrder + OrderItemList + OrderOperateHistory
        OmsOrderDetail res = new OmsOrderDetail();
        //OmsOrder
        OmsOrder order = orderMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(order, res);
        //OrderItemList
        OmsOrderItemExample itemExample = new OmsOrderItemExample();
        itemExample.createCriteria().andOrderIdEqualTo(id);
        List<OmsOrderItem> orderItemList = orderItemMapper.selectByExample(itemExample);
        res.setOrderItemList(orderItemList);
        //OrderItemList
        OmsOrderOperateHistoryExample operateHistoryExample = new OmsOrderOperateHistoryExample();
        operateHistoryExample.createCriteria().andOrderIdEqualTo(id);
        List<OmsOrderOperateHistory> orderOperateHistoryList = orderOperateHistoryMapper.selectByExample(operateHistoryExample);
        res.setOrderOperateHistoryList(orderOperateHistoryList);
        return res;
    }

    @Override
    public ConfirmOrderResult generateConfirmOrder(List<Long> cartIds) {
        ConfirmOrderResult result = new ConfirmOrderResult();

        //获取购物车信息
        Long memberId = 1L; //TODO memberService获取人员ID
        List<CartPromotionItem> cartPromotionItems = cartItemService.listPromotion(memberId, cartIds);
        result.setCartPromotionItemList(cartPromotionItems);

        //获取用户收货地址列表 TODO member相关service获取地址列表
        //获取用户可用优惠券列表 TODO member优惠券service获取优惠券列表
        //获取用户积分 TODO member.getIntegration
        //用户积分使用规则 TODO integration的mapper

        //计算总金额、活动优惠、应付金额
        ConfirmOrderResult.CalcAmount calcAmount = calCartAmount(cartPromotionItems);
        result.setCalcAmount(calcAmount);
        return result;
    }

    private ConfirmOrderResult.CalcAmount calCartAmount(List<CartPromotionItem> cartPromotionItems) {
        ConfirmOrderResult.CalcAmount result = new ConfirmOrderResult.CalcAmount();
        BigDecimal totalAmount = new BigDecimal(0);
        BigDecimal promotionAmount = new BigDecimal(0);
        for (CartPromotionItem cartPromotionItem : cartPromotionItems) {
            totalAmount.add(cartPromotionItem.getPrice().multiply(BigDecimal.valueOf(cartPromotionItem.getQuantity())));
            promotionAmount.add(cartPromotionItem.getReduceAccount().multiply(BigDecimal.valueOf(cartPromotionItem.getQuantity())));
        }
        result.setFreightAmount(new BigDecimal(0));
        return result;
    }

    @Override
    public Map<String, Object> generateOrder(OmsOrderParam omsOrderParam) {
        //获取购物车及优惠信息
        List<OmsOrderItem> orderItemList = new ArrayList<>();
        //TODO 获取当前会员 UmsMember
        UmsMember umsMember = new UmsMember();
        umsMember.setId(1L);
        List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(umsMember.getId(), omsOrderParam.getCartIds());
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            //生成下单商品信息 - 明细
            OmsOrderItem orderItem = new OmsOrderItem();
            orderItem.setProductId(cartPromotionItem.getProductId());
            orderItem.setProductName(cartPromotionItem.getProductName());
            orderItem.setProductAttr(cartPromotionItem.getProductAttr());
            orderItem.setProductBrand(cartPromotionItem.getProductBrand());
            orderItem.setProductPic(cartPromotionItem.getProductPic());
            orderItem.setGiftGrowth(cartPromotionItem.getGrowth());
            orderItem.setGiftIntegration(cartPromotionItem.getIntegration());
            orderItem.setProductCategoryId(cartPromotionItem.getProductCategoryId());
            orderItem.setProductQuantity(cartPromotionItem.getQuantity());
            orderItem.setProductPrice(cartPromotionItem.getPrice());
            orderItem.setProductSkuCode(cartPromotionItem.getProductSkuCode());
            orderItem.setProductSkuId(cartPromotionItem.getProductSkuId());
            orderItem.setProductSn(cartPromotionItem.getProductSn());
            orderItemList.add(orderItem);
        }
        //判断商品是否[都]有库存
        if (!hasStock(cartPromotionItemList)) {
            Assert.fail("库存不足，无法下单");
        }
        //是否使用积分
        if (omsOrderParam.getUserIntegration() == null || omsOrderParam.getUserIntegration() <= 0) {
            //不使用积分
            for (OmsOrderItem orderItem : orderItemList) {
                orderItem.setIntegrationAmount(new BigDecimal(0));
            }
        } else {
            //使用积分
            //计算总金额 totalAmount
            BigDecimal totalAmount = calcTotalAmount(orderItemList);
            //积分兑换的金额
            BigDecimal integrationAmount = getUserIntegrationAmount(omsOrderParam.getUserIntegration(), totalAmount, umsMember, omsOrderParam.getCouponId() != null);
            if (integrationAmount.compareTo(new BigDecimal(0)) == 0) {
                Assert.fail("积分不可使用");
            }
            //积分分配到每个订单里
            for (OmsOrderItem orderItem : orderItemList) {
                //TODO 这里的getProductPrice的price是什么价格，是sku的价格，还是product的价格，还是总价格(不是总价格，是单品价格)
                BigDecimal eachIntegrationAmount = orderItem.getProductPrice().divide(totalAmount, 2, RoundingMode.HALF_EVEN).multiply(integrationAmount);
                orderItem.setIntegrationAmount(eachIntegrationAmount);
            }

        }
        //计算orderItem的实付金额
        handleRealAmount(orderItemList);
        //库存锁定
        lockStock(cartPromotionItemList);

        //根据商品合计、运费、积分、优惠券、满减计算应付金额
        OmsOrder order = new OmsOrder();
        order.setDiscountAmount(new BigDecimal("0"));
        order.setFreightAmount(new BigDecimal("0"));
        order.setTotalAmount(calcTotalAmount(orderItemList));
        order.setPromotionAmount(calcPromotionAmount(orderItemList));
        order.setPromotionInfo(calcPromotionInfo(orderItemList));
        //优惠券 & 积分
        if (omsOrderParam.getCouponId() == null) {
            order.setCouponId(null);
            order.setCouponAmount(new BigDecimal("0"));
        } else {
            order.setCouponId(omsOrderParam.getCouponId());
            order.setCouponAmount(calcCouponAmount(orderItemList));
        }
        if (omsOrderParam.getUserIntegration() == null) {
            order.setIntegration(null);
            order.setIntegrationAmount(new BigDecimal("0"));
        } else {
            order.setIntegration(omsOrderParam.getUserIntegration());
            order.setIntegrationAmount(calcIntegrationAmount(orderItemList));
        }
        order.setPayAmount(calcPayAmount(order));
        //转化为订单信息
        order.setMemberId(umsMember.getId());
        order.setMemberUsername(umsMember.getUsername());
        order.setCreateTime(new Date());
        //支付方式： 0-未支付 1-支付宝 2-微信
        order.setPayType(omsOrderParam.getPayType());
        //订单来源： 0-PC  1-移动端
        order.setSourceType(1);
        //订单状态： 0-待付款 1-待发货 2-已发货 3-已完成 4-已关闭 5-无效订单
        order.setStatus(0);
        //订单类型： 0-正常订单 1-秒杀订单
        order.setOrderType(0);
        //收货人信息： 姓名、电话、地址、邮编 TODO memberService根据addressId查询收货人信息
        Long memberReceiveAddressId = omsOrderParam.getMemberReceiveAddressId();
        //确认状态、删除状态
        order.setDeleteStatus(0);
        order.setConfirmStatus(0);
        //计算赠送积分、赠送成长值
        order.setGrowth(calcGrowth(orderItemList));
        order.setIntegration(calcIntegration(orderItemList));
        //生成订单流水号
        order.setOrderSn(calcOrderSn(order));
        //设置自动收货天数 TODO 待处理，消息队列实现
        //插入order表和order_item表
        orderMapper.insert(order);
        for (OmsOrderItem orderItem : orderItemList) {
            orderItem.setOrderId(order.getId());
            orderItem.setOrderSn(order.getOrderSn());
        }
        orderItemDao.batchInsert(orderItemList);
        //TODO 若使用优惠券，更改优惠券使用状态
        //若使用积分，则扣除积分 TODO memberService ,积分是否已经使用的标准是：和前端约定好，传过来的话，则表示可以进行使用。由前端确定是否
        //                        积分和优惠券可以同时使用，同时显示提示信息，如："积分和优惠券不可以同时使用"。
        //删除购物车的下单商品
        deleteCartItemList(cartPromotionItemList);
        //发送延迟消息取消订单 TODO 消息队列实现延时取消订单
        //返回结果
        Map<String, Object> res = new HashMap<>();
        res.put("order", order);
        res.put("orderItem", orderItemList);
        return res;
    }

    //删除购物车下的物品
    private void deleteCartItemList(List<CartPromotionItem> cartPromotionItemList) {
        List<Long> cartIds = new ArrayList<>();
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            cartIds.add(cartPromotionItem.getId());

        }
        cartItemService.delete(1L, cartIds);
    }

    private BigDecimal calcPayAmount(OmsOrder order) {
        BigDecimal res = new BigDecimal("0");
        //总价格 + 邮费 - 促销优惠 - 积分抵扣 - 优惠券抵扣
        res = order.getTotalAmount()
                .add(order.getFreightAmount())
                .subtract(order.getPromotionAmount())
                .subtract(order.getIntegrationAmount())
                .subtract(order.getCouponAmount());
        return res;
    }

    //生成流水号  8位日期+2位平台好+2位支付方式+6位以上自增ID
    private String calcOrderSn(OmsOrder order) {
        StringBuilder sb = new StringBuilder();
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ORDER_ID + date;
        Long increment = redisService.incr(key, 1);
        sb.append(date);
        //右对齐，取2位。不足2位左侧补0，缺省为补空格。
        sb.append(String.format("%02d", order.getSourceType()));
        sb.append(String.format("%02d", order.getPayType()));
        String incrementStr = increment.toString();
        if (incrementStr.length() <= 6) {
            sb.append(String.format("%06d", increment));
        } else {
            sb.append(incrementStr);
        }
        return sb.toString();
    }

    private Integer calcIntegration(List<OmsOrderItem> orderItemList) {
        int res = 0;
        for (OmsOrderItem omsOrderItem : orderItemList) {
            res += omsOrderItem.getGiftIntegration() * omsOrderItem.getProductQuantity();
        }
        return res;
    }

    private Integer calcGrowth(List<OmsOrderItem> orderItemList) {
        int res = 0;
        for (OmsOrderItem omsOrderItem : orderItemList) {
            res += omsOrderItem.getGiftGrowth() * omsOrderItem.getProductQuantity();
        }
        return res;
    }

    private BigDecimal calcIntegrationAmount(List<OmsOrderItem> orderItemList) {
        BigDecimal res = new BigDecimal("0");
        for (OmsOrderItem omsOrderItem : orderItemList) {
            res.add(omsOrderItem.getIntegrationAmount().multiply(new BigDecimal(omsOrderItem.getProductQuantity())));
        }
        return res;
    }

    private BigDecimal calcCouponAmount(List<OmsOrderItem> orderItemList) {
        BigDecimal res = new BigDecimal("0");
        for (OmsOrderItem orderItem : orderItemList) {
            res.add(orderItem.getCouponAmount());
        }
        return res;
    }

    private String calcPromotionInfo(List<OmsOrderItem> orderItemList) {
        StringBuilder sb = new StringBuilder();
        for (OmsOrderItem orderItem : orderItemList) {
            sb.append(orderItem.getPromotionName()).append(orderItem.getPromotionAmount()).append(";");
        }
        String res = sb.toString();
        if (res.endsWith(";")) {
            res = res.substring(0, res.length() - 1);
        }
        return res;
    }

    private BigDecimal calcPromotionAmount(List<OmsOrderItem> orderItemList) {
        BigDecimal total = new BigDecimal("0");
        for (OmsOrderItem orderItem : orderItemList) {
            total.add(orderItem.getPromotionAmount());
        }
        return total;
    }

    //锁定库存
    private void lockStock(List<CartPromotionItem> cartPromotionItemList) {
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            PmsSkuStock skuStock = skuStockMapper.selectByPrimaryKey(cartPromotionItem.getProductSkuId());
            skuStock.setLockStock(skuStock.getLockStock() + cartPromotionItem.getQuantity());
            skuStockMapper.updateByPrimaryKey(skuStock);
        }

    }

    //计算真实价格
    private void handleRealAmount(List<OmsOrderItem> orderItemList) {
        for (OmsOrderItem orderItem : orderItemList) {
            BigDecimal realAmount = new BigDecimal("0");
            //原价 - 促销降低价格 - 积分抵扣价格 - 优惠券抵扣价格
            realAmount = orderItem.getProductPrice()
                    .subtract(orderItem.getPromotionAmount()) //促销降低的价格（每件）：单品促销、满减、阶梯折扣
                    .subtract(orderItem.getIntegrationAmount()) //积分降低的价格
                    .subtract(orderItem.getCouponAmount()); //优惠券降低的价格
            orderItem.setRealAmount(realAmount);
        }
    }

    //判断可用的积分点：判断本人所剩积分点是否够用、是否和优惠券冲突、是否超过了最高抵用百分比
    private BigDecimal getUserIntegrationAmount(Integer userIntegration, BigDecimal totalAmount, UmsMember umsMember, boolean couponUsed) {
        BigDecimal zeroAmount = new BigDecimal(0);
        //TODO 根据UmsMember查看用户的积分是否大于userIntegration
        //根据积分使用规则，判断是否可以使用
        UmsIntegrationConsumeSetting integrationConsumeSetting = integrationConsumeSettingMapper.selectByPrimaryKey(1L);
        //1、是否可以跟优惠券公用
        if (couponUsed && integrationConsumeSetting.getCouponStatus().equals(0)) {
            return zeroAmount;
        }
        //2、是否达到积分最低使用门槛
        if (userIntegration.compareTo(integrationConsumeSetting.getUseUnit()) < 0) {
            return zeroAmount;
        }
        //3、使用积分折成的金额，是否超过了要求的最大占比  totalAmount
        BigDecimal integrationAmount = new BigDecimal(userIntegration).divide(new BigDecimal(integrationConsumeSetting.getUseUnit()), 2, RoundingMode.HALF_EVEN);
        //最大百分比的金额
        BigDecimal maxIntegrationAmount = totalAmount.multiply(new BigDecimal(integrationConsumeSetting.getMaxPercentPerOrder())).divide(new BigDecimal("100"),2, RoundingMode.HALF_EVEN);
        if (zeroAmount.compareTo(maxIntegrationAmount) > 0) {
            return integrationAmount;
        }
        return zeroAmount;
    }

    //计算订单的总金额
    private BigDecimal calcTotalAmount(List<OmsOrderItem> orderItemList) {
        BigDecimal totalAmount = new BigDecimal(0);
        for (OmsOrderItem orderItem : orderItemList) {
            totalAmount.add(orderItem.getProductPrice().multiply(BigDecimal.valueOf(orderItem.getProductQuantity())));
        }
        return totalAmount;
    }

    private boolean hasStock(List<CartPromotionItem> cartPromotionItemList) {
        //如果库存 - 锁定库存为 <= 0 ，则返回false
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            if (cartPromotionItem.getRealStock() == null || cartPromotionItem.getRealStock() <= 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<OmsOrder> list(Integer pageNum, Integer pageSize, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        OmsOrderExample orderExample = new OmsOrderExample();
        orderExample.createCriteria().andStatusEqualTo(status);
        List<OmsOrder> orderList = orderMapper.selectByExample(orderExample);
        return orderList;
    }

    @Override
    public Integer paySuccessCallback(Long orderId, Integer payType) {
        //更新order
        OmsOrder order = new OmsOrder();
        order.setId(orderId);
        order.setModifyTime(new Date());
        order.setPayType(payType);
        orderMapper.updateByPrimaryKeySelective(order);
        //更细sku的stock和lock_stock
        OmsOrderDetail detail = orderDao.detail(orderId);
        int count = portalOrderDao.batchUpdate(detail.getOrderItemList());
        return count;
    }
}
package com.zzy.malladmin.service.impl;

import cn.hutool.core.text.StrBuilder;
import com.zzy.malladmin.dao.PortalProductDao;
import com.zzy.malladmin.dto.CartPromotionItem;
import com.zzy.malladmin.mbg.model.*;
import com.zzy.malladmin.model.PromotionProduct;
import com.zzy.malladmin.service.OmsPromotionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: mall_learning
 * @description:
 * @author: zzy
 * @create: 2023-12-16
 */
@Service
public class OmsPromotionServiceImpl implements OmsPromotionService {

    @Autowired
    PortalProductDao portalProductDao;
    @Override
    public List<CartPromotionItem> calcCartPromotion(List<OmsCartItem> cartItemList) {
        //1、根据商品id对cartItemList分组 以spu为单位计算 Map<Long,List<OmsCartItem>>
        Map<Long, List<OmsCartItem>> productCartMap  = groupByProduct(cartItemList);
        //2、查询所有商品的优惠信息 List<PromotionProduct>
        List<PromotionProduct> promotionProductList = getPromotionList(cartItemList);
        //3、根据商品促销类型计算商品促销价格
        List<CartPromotionItem> cartPromotionItemList = new ArrayList<>();
        for (Map.Entry<Long, List<OmsCartItem>> entry : productCartMap.entrySet()) {
            //对spu商品分别计算
            Long productId = entry.getKey();
            List<OmsCartItem> omsCartItems = entry.getValue();
            PromotionProduct promotionProduct = getPromotionProductById(productId, promotionProductList);
            Integer promotionType = promotionProduct.getPromotionType();
            if (promotionType == 1) {
                //单品促销
                for (OmsCartItem omsCartItem : omsCartItems) {
                    CartPromotionItem cartPromotionItem = new CartPromotionItem();
                    BeanUtils.copyProperties(omsCartItem, cartPromotionItem);
                    PmsSkuStock skuStock = getOriginalPrice(promotionProduct, omsCartItem.getProductSkuId());
                    cartPromotionItem.setPrice(skuStock.getPrice());
                    cartPromotionItem.setRealStock(promotionProduct.getStock() - skuStock.getLockStock());
                    cartPromotionItem.setPromotionMessage("单品促销");
                    cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
                    cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
                    cartPromotionItem.setReduceAccount(skuStock.getPrice().subtract(skuStock.getPromotionPrice()));
                    cartPromotionItemList.add(cartPromotionItem);
                }
            } else if (promotionType == 3){
                //打折优惠
                //商品打折情况
                List<PmsProductLadder> ladderList = getPromotionLadderList(productId, promotionProduct);
                int count = getCount(omsCartItems);
                PmsProductLadder ladder =  getPromotionLadder(count, ladderList);
                if (ladder == null) {
                    handleNoReduce(cartPromotionItemList, cartItemList, promotionProduct);
                } else {
                    for (OmsCartItem omsCartItem : omsCartItems) {
                        CartPromotionItem cartPromotionItem = new CartPromotionItem();
                        BeanUtils.copyProperties(omsCartItem, cartPromotionItem);
                        PmsSkuStock skuStock = getOriginalPrice(promotionProduct, omsCartItem.getProductSkuId());
                        BigDecimal originalPrice = skuStock.getPrice();
                        cartPromotionItem.setPromotionMessage(getDiscountMessage(count,ladder));
                        cartPromotionItem.setIntegration(promotionProduct.getGiftGrowth());
                        cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
                        cartPromotionItem.setReduceAccount(originalPrice.subtract(ladder.getDiscount().multiply(originalPrice)));
                        cartPromotionItemList.add(cartPromotionItem);
                    }
                }
            } else if (promotionType == 4) {
                //满减
                //商品满减情况
                List<PmsProductFullReduction> fullReductionList = getPromotionFullReductionList(productId, promotionProduct);
                BigDecimal totalPrice = getTotalPrice(omsCartItems,promotionProductList);
                PmsProductFullReduction fullReduction = getPromotionFullReduction(totalPrice, fullReductionList);
                if (fullReduction == null) {
                    handleNoReduce(cartPromotionItemList, cartItemList, promotionProduct);
                } else {
                    for (OmsCartItem omsCartItem : omsCartItems) {
                        CartPromotionItem cartPromotionItem = new CartPromotionItem();
                        cartPromotionItem.setPromotionMessage(getFullReductionMessage(totalPrice, fullReduction));
                        PmsSkuStock skuStock = getOriginalPrice(promotionProduct, omsCartItem.getProductSkuId());
                        BigDecimal originalPrice = skuStock.getPrice();
                        cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
                        cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
                        cartPromotionItem.setRealStock(promotionProduct.getStock() - skuStock.getLockStock());
                        cartPromotionItem.setReduceAccount(originalPrice.divide(totalPrice, RoundingMode.HALF_EVEN)
                                .multiply(fullReduction.getReducePrice()));
                        cartPromotionItemList.add(cartPromotionItem);
                    }
                }
            } else {
                //没有降价的商品 的处理方法
                handleNoReduce(cartPromotionItemList, cartItemList, promotionProduct);
            }

        }
        return cartPromotionItemList;

    }

    private String getFullReductionMessage(BigDecimal totalPrice, PmsProductFullReduction fullReduction) {
        StrBuilder sb = new StrBuilder();
        sb.append("满").append(fullReduction.getFullPrice())
                .append("减").append(fullReduction.getReducePrice());
        return sb.toString();
    }

    private PmsProductFullReduction getPromotionFullReduction(BigDecimal totalPrice, List<PmsProductFullReduction> fullReductionList) {
        PmsProductFullReduction result = null;
        for (PmsProductFullReduction fullReduction : fullReductionList) {
            if (totalPrice.compareTo(fullReduction.getFullPrice()) > 0) {
                result = fullReduction;
            } else {
                break;
            }
        }
        return result;
    }

    private BigDecimal getTotalPrice(List<OmsCartItem> omsCartItems,List<PromotionProduct> promotionProductList) {
        BigDecimal result = new BigDecimal(0);
        for (OmsCartItem omsCartItem : omsCartItems) {
            PromotionProduct promotionProduct = getPromotionProductById(omsCartItem.getProductId(), promotionProductList);
            //TODO 这里的omsCartItem.getPrice()不能作为原价：每个sku的price不一样，计算总价时，使用sku*count求和
            //BigDecimal oneSkuTotal = omsCartItem.getPrice().multiply(BigDecimal.valueOf(omsCartItem.getQuantity()));
            PmsSkuStock skuStock = getOriginalPrice(promotionProduct, omsCartItem.getProductSkuId());
            BigDecimal perSkuTotal = skuStock.getPrice().multiply(BigDecimal.valueOf(omsCartItem.getQuantity()));
            result.add(perSkuTotal);
        }
        return result;
    }

    private List<PmsProductFullReduction> getPromotionFullReductionList(Long productId, PromotionProduct promotionProduct) {
        List<PmsProductFullReduction> fullReductionList = promotionProduct.getFullReductionList();
        return fullReductionList.stream()
                .filter(fullReduction -> fullReduction.getProductId() == productId).collect(Collectors.toList());
    }

    private PmsProductLadder getPromotionLadder(int count, List<PmsProductLadder> ladderList) {
        for (PmsProductLadder ladder : ladderList) {
            if (ladder.getCount() == count) {
                return ladder;
            }
        }
        return null;
    }

    private String getDiscountMessage(int count,PmsProductLadder ladder) {
        StrBuilder sb = new StrBuilder();
        sb.append("满").append(count).append("件")
                .append("打").append(ladder.getCount()).append("折");
        return sb.toString();
    }

    private int getCount(List<OmsCartItem> omsCartItems) {
        int count = 0;
        for (OmsCartItem omsCartItem : omsCartItems) {
            count += omsCartItem.getQuantity();
        }
        return count;
    }

    private List<PmsProductLadder> getPromotionLadderList(Long productId, PromotionProduct promotionProduct) {
        List<PmsProductLadder> productLadderList = promotionProduct.getProductLadderList();
        return productLadderList.stream()
                .filter(ladder -> ladder.getProductId().equals(productId)).collect(Collectors.toList());

    }

    //对没有满足优惠条件的商品进行处理
    private void handleNoReduce(List<CartPromotionItem> cartPromotionItemList, List<OmsCartItem> cartItemList, PromotionProduct promotionProduct) {
        for (OmsCartItem omsCartItem : cartItemList) {
            CartPromotionItem cartPromotionItem = new CartPromotionItem();
            BeanUtils.copyProperties(omsCartItem, cartPromotionItem);
            cartPromotionItem.setPromotionMessage("无优惠");
            cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
            cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
            cartPromotionItem.setReduceAccount(new BigDecimal(0));
            PmsSkuStock skuStock = getOriginalPrice(promotionProduct, omsCartItem.getProductSkuId());
            if (skuStock != null) {
                cartPromotionItem.setRealStock(promotionProduct.getStock() - skuStock.getStock());
            }
            cartPromotionItemList.add(cartPromotionItem);
        }
    }

    private PmsSkuStock getOriginalPrice(PromotionProduct promotionProduct, Long productSkuId) {
        List<PmsSkuStock> skuStockList = promotionProduct.getSkuStockList();
        for (PmsSkuStock skuStock : skuStockList) {
            if (skuStock.getId().equals(productSkuId)) {
                return skuStock;
            }
        }
        return null;
    }

    private PromotionProduct getPromotionProductById(Long productId, List<PromotionProduct> promotionProductList) {
        for (PromotionProduct promotionProduct : promotionProductList) {
            if (productId.equals(promotionProduct.getId())) {
                return promotionProduct;
            }
        }
        return null;
    }

    //将carItemList改为PromotionProductList
    private List<PromotionProduct> getPromotionList(List<OmsCartItem> cartItemList) {
        //获取购物车列表中的商品id
        Set<Long> productSet = new HashSet<>();
        for (OmsCartItem cartItem : cartItemList) {
            productSet.add(cartItem.getProductId());
        }
        List<PromotionProduct> promotionProductList = portalProductDao.getPromotionProductList(productSet);
        return promotionProductList;
    }

    private Map<Long, List<OmsCartItem>> groupByProduct(List<OmsCartItem> cartItemList) {
        Map<Long, List<OmsCartItem>> result = new HashMap<>();
        //遍历cartItemList，key为productId，value为对应的OmsCartItemList：存在则插入，不存在则新建
        for (OmsCartItem cartItem : cartItemList) {
            Long productId = cartItem.getId();
            List<OmsCartItem> cartItems = result.get(productId);
            if (cartItems == null) {
                cartItems = new ArrayList<>();
                cartItems.add(cartItem);
                result.put(productId, cartItems);
            } else {
                cartItems.add(cartItem);
            }
        }
        return result;
    }
}
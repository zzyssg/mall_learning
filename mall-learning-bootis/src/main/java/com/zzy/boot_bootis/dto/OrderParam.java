package com.zzy.boot_bootis.dto;

import lombok.Data;

/**
 * @ClassName OrderParam
 * @Author ZZy
 * @Date 2023/9/17 23:40
 * @Description
 * @Version 1.0
 */
@Data
public class OrderParam {



    //会员收获地址
    private Long memberReceiveAddressId;


    //优惠券ID
    private Long couponId;

    //使用的积分数
    private Integer useIntegration;

    //支付方式
    private Integer payType;

}

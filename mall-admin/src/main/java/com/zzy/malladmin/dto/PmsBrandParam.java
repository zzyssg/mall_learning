/**
 * @program: mall_learning-main
 * @description: pmsBrand_dto
 * @author: zzy
 * @create: 2023-11-19
 */
package com.zzy.malladmin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PmsBrandParam {

    private String name;

    @ApiModelProperty(value="首字母")
    private String firstLetter;

    private Integer sort;

    @ApiModelProperty(value="是否为品牌制造商：0->不是；1->是")
    private Integer factoryStatus;

    private Integer showStatus;

    @ApiModelProperty(value="产品数量")
    private Integer productCount;

    @ApiModelProperty(value="产品评论数量")
    private Integer productCommentCount;

    @ApiModelProperty(value="品牌logo")
    private String logo;

    @ApiModelProperty(value="专区大图")
    private String bigPic;

    @ApiModelProperty(value="品牌故事")
    private String brandStory;

    private static final long serialVersionUID = 1L;

}
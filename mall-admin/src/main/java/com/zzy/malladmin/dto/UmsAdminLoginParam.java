package com.zzy.malladmin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName UmsAdminLoginParam
 * @Author ZZy
 * @Date 2023/10/6 17:27
 * @Description
 * @Version 1.0
 */
@Data
@ApiModel
public class UmsAdminLoginParam {

    @ApiModelProperty(value = "账号")
    @NotNull
    private  String username;

    @ApiModelProperty(value = "密码")
    @NotNull
    private  String password;

}

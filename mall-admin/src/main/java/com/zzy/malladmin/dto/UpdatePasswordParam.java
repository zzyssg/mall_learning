package com.zzy.malladmin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @ClassName UpdatePasswordwordParam
 * @Author ZZy
 * @Date 2023/10/14 23:17
 * @Description
 * @Version 1.0
 */
@Data
public class UpdatePasswordParam {

    @NotEmpty
    @ApiModelProperty(value = "账号",required = true)
    private String username;

    @NotEmpty
    @ApiModelProperty(value = "旧密码",required = true)
    private String oldPassword;

    @NotEmpty
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;

}

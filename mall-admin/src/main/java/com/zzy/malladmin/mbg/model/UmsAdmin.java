package com.zzy.malladmin.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class UmsAdmin implements Serializable {
    private Long id;

    private String username;

    private String password;

    @ApiModelProperty(value="头像")
    private String icon;

    @ApiModelProperty(value="邮箱")
    private String email;

    @ApiModelProperty(value="昵称")
    private String nickName;

    @ApiModelProperty(value="备注信息")
    private String note;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty(value="最后登录时间")
    private Date loginTime;

    @ApiModelProperty(value="帐号启用状态：0->禁用；1->启用")
    private Integer status;

    private static final long serialVersionUID = 1L;

    public UmsAdmin() {

    }

    public UmsAdmin(Long id, String username, String password, String icon, String email, String nickName, String note, Date createTime, Date loginTime, Integer status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.icon = icon;
        this.email = email;
        this.nickName = nickName;
        this.note = note;
        this.createTime = createTime;
        this.loginTime = loginTime;
        this.status = status;
    }
}
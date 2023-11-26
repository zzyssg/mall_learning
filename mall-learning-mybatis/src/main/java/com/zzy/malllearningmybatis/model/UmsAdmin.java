package com.zzy.malllearningmybatis.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@Data
public class UmsAdmin implements Serializable {

    private Long id;

    private String username;

    private String password;

    private String icon;

    private String email;

    private  String nickName;

    private String note;

    private Date createTime;

    private Date loginTime;

    private Integer status;


}

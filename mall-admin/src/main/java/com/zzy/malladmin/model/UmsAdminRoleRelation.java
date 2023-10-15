package com.zzy.malladmin.model;

import lombok.Data;

/**
 * @ClassName UmsAdminRoleRelation
 * @Author ZZy
 * @Date 2023/10/15 0:24
 * @Description
 * @Version 1.0
 */
@Data
public class UmsAdminRoleRelation {

    private Long id;
    private Long adminId;
    private Integer roleId;
    private static final long serialVersionUID = 1L;

}

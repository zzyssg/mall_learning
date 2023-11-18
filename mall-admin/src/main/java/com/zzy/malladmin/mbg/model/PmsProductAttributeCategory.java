package com.zzy.malladmin.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

public class PmsProductAttributeCategory implements Serializable {
    private Long id;

    private String name;

    @ApiModelProperty(value="属性数量")
    private Integer attributeCount;

    @ApiModelProperty(value="参数数量")
    private Integer paramCount;

    private static final long serialVersionUID = 1L;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setAttributeCount(Integer attributeCount) {
        this.attributeCount = attributeCount;
    }

    public void setParamCount(Integer paramCount) {
        this.paramCount = paramCount;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAttributeCount() {
        return attributeCount;
    }

    public Integer getParamCount() {
        return paramCount;
    }

    @Override
    public String toString() {
        return "PmsProductAttributeCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", attributeCount=" + attributeCount +
                ", paramCount=" + paramCount +
                '}';
    }
}
package com.zzy.boot_bootis.nosql.elasticsearch.document;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @ClassName EsProductAttributeValue
 * @Author ZZy
 * @Date 2023/9/14 11:13
 * @Description
 * @Version 1.0
 */
@Data
public class EsProductAttributeValue implements Serializable {
    public static final Long serialVersionUId = 1L;

    private Long id;

    private Long productAttributeId;

    //属性值
    @Field(type = FieldType.Keyword)
    private  String value;

    //0：规格 1：参数
    private Integer type;

    //属性名称
    @Field(type = FieldType.Keyword)
    private String name;
}

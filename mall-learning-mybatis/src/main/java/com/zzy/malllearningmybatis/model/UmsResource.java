package com.zzy.malllearningmybatis.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class UmsResource {

    private Long id;
    private Date createTime;
    private String name;
    private String url;
    private String description;
    private int categoryId;

}

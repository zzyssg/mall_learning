package com.zzy.malllearningmybatis.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class UmsResourceCategory {

    private Long id;
    private Date createTime;
    private String name;
    private int sort;
}

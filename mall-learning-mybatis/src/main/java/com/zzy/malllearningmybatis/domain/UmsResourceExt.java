package com.zzy.malllearningmybatis.domain;

import com.zzy.malllearningmybatis.model.UmsResource;
import com.zzy.malllearningmybatis.model.UmsResourceCategory;
import lombok.Data;

@Data
public class UmsResourceExt extends UmsResource {
    private UmsResourceCategory category;

}

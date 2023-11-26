package com.zzy.malladmin.dto;

import com.zzy.malladmin.mbg.model.UmsMenu;
import lombok.Data;

import java.util.List;

/**
 * @ClassName UmsMenuNode
 * @Author ZZy
 * @Date 2023/10/15 16:44
 * @Description
 * @Version 1.0
 */
@Data
public class UmsMenuNode extends UmsMenu {

    public List<UmsMenuNode> children;

}

package com.zzy.boot_bootis.nosql.mongodb.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @ClassName MemberReadHistory
 * @Author ZZy
 * @Date 2023/9/16 20:46
 * @Description
 * @Version 1.0
 */
@Data
@Document
public class MemberReadHistory {

    @Id
    private String id;

    @Indexed
    private Long memberId;

    private String memberNickName;

    private String memberIcon;

    @Indexed
    private Long productId;

    private String productName;

    private String productPic;

    private String productSubTitle;

    private String productPrice;

    private Date createTime;

}

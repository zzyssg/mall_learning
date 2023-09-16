package com.zzy.boot_bootis.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName MinioUploadDto
 * @Author ZZy
 * @Date 2023/9/17 0:14
 * @Description
 * @Version 1.0
 */
@Data
public class MinioUploadDto {

    @ApiModelProperty(value = "文件地址")
    private String url;
    @ApiModelProperty(value = "文件名称")
    private String name;

}

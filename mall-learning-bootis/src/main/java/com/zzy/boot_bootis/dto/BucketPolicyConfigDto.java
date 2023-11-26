package com.zzy.boot_bootis.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName BucketPolicyConfigDto
 * @Author ZZy
 * @Date 2023/9/17 0:11
 * @Description
 * @Version 1.0
 */
@Data
@Builder
public class BucketPolicyConfigDto {

    private String Version;

    private List<Statement> Statement;


    @Data
    @Builder
    public static class Statement {

        private String Action;
        private String Effect;
        private String Principal;
        private String Resource;
    }
}

package com.zzy.boot_bootis.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.zzy.boot_bootis.common.api.CommonResult;
import com.zzy.boot_bootis.dto.BucketPolicyConfigDto;
import com.zzy.boot_bootis.dto.MinioUploadDto;
import io.minio.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName MinioController
 * @Author ZZy
 * @Date 2023/9/16 23:56
 * @Description
 * @Version 1.0
 */
@Api(tags = "MinioController")
@Tag(name = "MinioController", description = "文件上传")
@RestController
@RequestMapping("/minio")
public class MinioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinioController.class);

    @Value("${minio.endpoint}")
    private String ENDPOINT;
    @Value("${minio.bucketName}")
    private String BUCKET_NAME;
    @Value("${minio.accessKey}")
    private String ACCESS_KEY;
    @Value("${minio.secretKey}")
    private String SECRET_KEY;

    @ApiOperation(value = "文件上传")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public CommonResult uploadFile(@RequestPart("file") MultipartFile file) {
        try {
            //创建minio的java对象
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY, SECRET_KEY)
                    .build();
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
            if (bucketExists) {
                LOGGER.info("存储桶已经存在");
            } else {
                //创建桶，设置只读权限
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
                BucketPolicyConfigDto bucketPolicyConfigDto = createBucketPolicyConfigDto(BUCKET_NAME);
                SetBucketPolicyArgs config = SetBucketPolicyArgs.builder().bucket(BUCKET_NAME)
                        .config(JSONUtil.toJsonStr(bucketPolicyConfigDto))
                        .build();
                minioClient.setBucketPolicy(config);
            }
            String filename = file.getOriginalFilename();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            //设置存储对象名称
            String objectName = simpleDateFormat.format(new Date()) + "/" + filename;
            //使用putObject上传文件到存储捅中
            PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(BUCKET_NAME).object(objectName).contentType(file.getContentType()).stream(
                    file.getInputStream(), file.getSize(), ObjectWriteArgs.MIN_MULTIPART_SIZE
            ).build();
            minioClient.putObject(putObjectArgs);
            LOGGER.info("文件上传成功");
            MinioUploadDto minioUploadDto = new MinioUploadDto();
            minioUploadDto.setName(objectName);
            minioUploadDto.setUrl(ENDPOINT + "/"+BUCKET_NAME+"/" + objectName);
            return CommonResult.success(minioUploadDto);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("文件上传失败：{}", e.getMessage());
        }

        return CommonResult.failed();
    }

    private BucketPolicyConfigDto createBucketPolicyConfigDto(String bucket_name) {
        BucketPolicyConfigDto.Statement statement = BucketPolicyConfigDto.Statement.builder()
                .Action("s3:GetObject")
                .Effect("Allow")
                .Principal("*")
                .Resource("arn:aws:s3:::" + bucket_name + "/*.**")
                .build();
        return BucketPolicyConfigDto.builder().Version("2012-10-17")
                .Statement(CollUtil.toList(statement))
                .build();
    }


    @ApiOperation(value = "删除文件")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public CommonResult delete(@RequestParam("objectName") String objectName) {
        try {
            MinioClient minioClient = MinioClient.builder().endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY, SECRET_KEY)
                    .build();
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(BUCKET_NAME).object(objectName).build());
            return CommonResult.success(null);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("删除文件失败：{}", objectName);
        }
        return CommonResult.failed("删除文件失败");
    }

}

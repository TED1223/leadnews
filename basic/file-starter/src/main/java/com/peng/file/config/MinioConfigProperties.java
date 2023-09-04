package com.peng.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @author: pengshengfeng
 * @date: 2023/9/4 17:07
 * @description:
 */
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioConfigProperties implements Serializable {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String endpoint;
    private String readPath;
}

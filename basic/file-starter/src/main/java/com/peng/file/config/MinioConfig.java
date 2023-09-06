package com.peng.file.config;

import com.peng.file.service.FileStorageService;
import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: pengshengfeng
 * @date: 2023/9/4 17:06
 * @description:
 */
@Data
@Configuration
@EnableConfigurationProperties({MinioConfigProperties.class})
//当引入FileStorageService接口时
@ConditionalOnClass(FileStorageService.class)
public class MinioConfig {

    @Autowired
    private MinioConfigProperties minioConfigProperties;

    @Bean
    public MinioClient buildMinioClient(){
        return MinioClient.builder()
                .credentials(minioConfigProperties.getAccessKey(), minioConfigProperties.getSecretKey())
                .endpoint(minioConfigProperties.getEndpoint())
                .build();
    }
}

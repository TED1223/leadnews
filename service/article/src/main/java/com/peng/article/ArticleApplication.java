package com.peng.article;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: pengshengfeng
 * @date: 2023/9/4 11:01
 * @description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.peng.article.mapper")
public class ArticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class,args);
    }
}

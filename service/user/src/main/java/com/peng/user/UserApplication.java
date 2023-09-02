package com.peng.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: pf
 * @CreateTime: 2023-09-02  14:38
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.peng.user.mapper")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}

package java.com.peng.wemedia.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: pf
 * @CreateTime: 2023-09-02  16:59
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient //开启注册中心
public class WemediaGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WemediaGatewayApplication.class,args);
    }
}

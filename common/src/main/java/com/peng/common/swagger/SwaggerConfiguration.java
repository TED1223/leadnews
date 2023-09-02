package com.peng.common.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: pf
 * @CreateTime: 2023-09-02  16:32
 * @Description:  swagger配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket buildDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.peng"))
                .build();
    }

    private ApiInfo buildApiInfo(){
        Contact contact = new Contact("peng","","");
        return new ApiInfoBuilder()
                .title("api")
                .description("黑马头条后台api")
                .contact(contact)
                .version("1.0.0")
                .build();
    }

}

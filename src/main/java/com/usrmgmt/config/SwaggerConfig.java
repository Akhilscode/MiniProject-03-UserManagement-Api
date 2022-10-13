package com.usrmgmt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    Docket apiDoc() {
        return new Docket(DocumentationType.SWAGGER_2).
                select().
                apis(RequestHandlerSelectors.basePackage("com.usrmgmt.controller")).
                paths(PathSelectors.any())
                .build();
    }

}


//http://localhost:{serverport}/swagger-ui.html   use this url to access the swagger.
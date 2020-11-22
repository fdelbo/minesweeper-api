package com.deviget.minesweeper.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket(ServletContext servletContext) {
        return new Docket(DocumentationType.SWAGGER_2)
                .tags(new Tag("Users", "Operations related to users"),
                        new Tag("Games", "Operations related to games"))
                .groupName("minesweeper").select()
                .apis(RequestHandlerSelectors.basePackage("com.deviget.minesweeper.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .pathProvider(new RelativePathProvider(servletContext)).select()
                .paths(Predicates.and(Predicates.not(PathSelectors.regex("/error.*")),
                        Predicates.not(PathSelectors.regex("/actuator.*"))))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Minesweeper API")
                .description("A beautiful minesweeper implemented with java")
                .build();
    }
}

package com.vdias.expensetrckr.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Class responsible for configuring Swagger.
 */
@Configuration
@PropertySource(value = {"classpath:application.properties"})
public class SwaggerConfig {
    /**
     * Swagger title.
     */
    @Value("${app.swagger.api.title}")
    private String title;

    /**
     * Swagger description.
     */
    @Value("${app.swagger.api.description}")
    private String description;

    /**
     * Application version to show in Swagger.
     */
    @Value("${app.swagger.api.version}")
    private String version;

    /**
     * Base package that is displayed by Swagger.
     */
    @Value("${app.swagger.api.basePackage}")
    private String basePackage;

    /**
     * Project license to show in Swagger.
     */
    @Value("${app.swagger.api.license}")
    private String license;

    /**
     * Url of the project license to show in Swagger.
     */
    @Value("${app.swagger.api.licenseUrl}")
    private String licenseUrl;

    /**
     * Builds a {@link Docket} bean with the Swagger configuration.
     *
     * @return the swagger configuration
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .license(license)
                .licenseUrl(licenseUrl)
                .build();
    }
}

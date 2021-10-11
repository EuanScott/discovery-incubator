package com.example.springplayground.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider(InMemorySwaggerResourcesProvider defaultResourcesProvider) {

        // https://stackoverflow.com/q/56044479
        // https://songrgg.github.io/operation/host-swagger-documentation-with-yaml-json-files/

        // TODO: Figure out why the models are not not being generated in the UI
        return () -> {
            SwaggerResource wsResource = new SwaggerResource();
            wsResource.setName("Controller Docs");
            wsResource.setSwaggerVersion("2.0");
            wsResource.setLocation("/swagger/controller-config.yml");

            SwaggerResource wsResource2 = new SwaggerResource();
            wsResource2.setName("Service Docs");
            wsResource2.setSwaggerVersion("2.0");
            wsResource2.setLocation("/swagger/service-config.yml");

            List<SwaggerResource> resources = new ArrayList<>(defaultResourcesProvider.get());
            resources.clear();
            resources.add(wsResource);
            resources.add(wsResource2);

            return resources;
        };
    }
}

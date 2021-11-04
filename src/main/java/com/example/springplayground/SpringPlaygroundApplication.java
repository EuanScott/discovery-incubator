package com.example.springplayground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.WebApplicationInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableCaching
public class SpringPlaygroundApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
    //    public static final String APPLICATION_NAME = "integrated-rewards-msrv";
    public static void main(String[] args) {
        SpringApplication.run(SpringPlaygroundApplication.class, args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringPlaygroundApplication.class);
    }
}

package com.example.springplayground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SpringPlaygroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPlaygroundApplication.class, args);

        System.out.println("Starting the Playground Application");
    }


    /*
      TODO:
       Understand the different types of Beans and what exactly they are as well as how they are used
       Understand what Profiling is
       Create yml file to generate models and interfaces for me
       Swagger 2 code gen controller example

       PLugins are the glue to stick everything together
     */
}

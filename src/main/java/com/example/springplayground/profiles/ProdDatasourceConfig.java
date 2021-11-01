package com.example.springplayground.profiles;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ProdDatasourceConfig implements DatasourceConfig {

    @Override
    public void setup() {
        System.out.println("\nSetting up datasource for PRODUCTION environment.\n");
    }

}
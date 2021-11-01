package com.example.springplayground.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class testProfileSetup {
    @Autowired
    DatasourceConfig datasourceConfig;

    @Bean
    public void setupDatasource() {
        datasourceConfig.setup();
    }
}

package com.example.springplayground.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class testProfileSetup {
    @Autowired
    DataSourceConfig dataSourceConfig;

    @Bean
    public void setupDatasource() {
        dataSourceConfig.setup();
    }
}

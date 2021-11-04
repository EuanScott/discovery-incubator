package com.example.springplayground.setup;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class TestApplicationConfiguration {

    public WireMockServer wireMockServer;

    @Test
    @Bean("wireMockServer")
    public WireMockServer wireMockServer() {
        return wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
    }
}

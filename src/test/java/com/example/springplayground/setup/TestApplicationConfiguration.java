package com.example.springplayground.setup;

import com.example.springplayground.util.ApplicationConfiguration;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Configuration
@EnableAutoConfiguration
@Import({ApplicationConfiguration.class})
public class TestApplicationConfiguration {

    private static final int SERVER_PORT = 8087;
    private static final String SERVER_HOST = "http://localhost:" + SERVER_PORT;

    @Bean("wireMockServer")
    @Profile(value = "test")
    public WireMockServer wireMockServer() {
        WireMockServer wireMockServer = new WireMockServer(SERVER_PORT);

        if (!wireMockServer.isRunning()) {
            wireMockServer.start();
        }

        return wireMockServer;
    }

    @Bean("retrofitServiceEnv")
    @Profile("test")
    public String retrofitServiceEnv() {
        return SERVER_HOST;
    }
}

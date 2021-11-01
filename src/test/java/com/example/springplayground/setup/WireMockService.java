package com.example.springplayground.setup;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class WireMockService {

    static WireMockServer wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Starting the server");
        wireMockServer.start();
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("Stopping the server");
        wireMockServer.stop();
    }

    @AfterEach
    public void afterEach() {
        // wireMockRule.resetAll();
    }
}
package com.example.springplayground.api.issue;

import com.example.springplayground.SpringPlaygroundApplication;
import com.example.springplayground.setup.TestApplicationConfiguration;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@ContextConfiguration(classes = {SpringPlaygroundApplication.class, TestApplicationConfiguration.class})
@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class IssueControllerTest {

    private IssueController issueControllerTest;

    @Autowired // Only acceptable in tests
    private IssueService issueService;

    @Autowired // Only acceptable in tests
    private WireMockServer wireMockServer;

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Before
    public void setup() {
        wireMockServer.resetMappings();
        wireMockServer.resetRequests();
        wireMockServer.resetToDefaultMappings();
    }

    @Test
    public void httpStatusOkay() throws IOException {

        System.out.println("A-Okay");

        // TODO: 11/9/2021 What is this meant for exactly?
        // Can call from postman
        wireMockServer.stubFor(post(urlMatching("/api/Issues"))
                .withRequestBody(equalToJson("{\"title\": null}"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                )
        );

        // TODO: 11/9/2021 How can I get this to call my custom endpoint and not 3rd party endpoint?
        // HttpGet request = new HttpGet("http://frontendshowcase.azurewebsites.net/api/Issues");
        HttpPost request = new HttpPost("http://localhost:8087/api/issues");
        HttpResponse httpResponse = httpClient.execute(request);

        int responseStatusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("Response Status Code: " + responseStatusCode);

        assertEquals(200, responseStatusCode);
    }

    @Test
    public void httpStatusNotFound() throws IOException {
        System.out.println("A-Not-Okay");

        wireMockServer.stubFor(post(urlMatching("/api/issues"))
                .withRequestBody(equalToJson("{\"title\": null}"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                )
        );

        HttpGet request = new HttpGet("http://frontendshowcase.azurewebsites.net/api/Issue");
        HttpResponse httpResponse = httpClient.execute(request);

        int responseStatusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("Response Status Code: " + responseStatusCode);

        assertEquals(404, responseStatusCode);
    }

    @Test
    public void testRequestBody() throws IOException {
        System.out.println("Request Body Test");

        wireMockServer.stubFor(post(urlMatching("/api/issues"))
                .withRequestBody(equalToJson("{\"title\": \"captain\"}"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                )
        );
    }

    // @Test
    // public void testResponseBody() throws IOException {
    //     System.out.println("Response Body Test");
    //
    //     wireMockServer.stubFor(post(urlMatching("/api/issues"))
    //             .withRequestBody(equalToJson("{\"title\": null}"))
    //             .withHeader("Content-Type", containing("application/json"))
    //             .willReturn(aResponse()
    //                     .withStatus(HttpStatus.OK.value())
    //                     .withBody(String.valueOf(equalToJson("{}")))
    //             )
    //     );
    //
    //     HttpGet request = new HttpGet("http://frontendshowcase.azurewebsites.net/api/Issues");
    //     HttpResponse httpResponse = httpClient.execute(request);
    //
    //     String path = request.getURI().getPath();
    //     System.out.println("Request Path: " + path);
    //
    //     assertEquals("/api/Issues", path);
    // }
}
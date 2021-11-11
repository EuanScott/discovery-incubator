package com.example.springplayground.api.issue;

import com.example.springplayground.SpringPlaygroundApplication;
import com.example.springplayground.setup.TestApplicationConfiguration;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired // Only acceptable in tests
    private WireMockServer wireMockServer;

    @Autowired
    @Qualifier("retrofitServiceEnv")
    private String wireMockURL;

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Before
    public void setup() {
        System.out.println("setup");
        wireMockServer.resetMappings();
        wireMockServer.resetRequests();
        wireMockServer.resetToDefaultMappings();
    }

    @Test
    public void getIssuesHttpOkay() throws IOException {

        System.out.println("getIssues A-Okay");

        wireMockServer.stubFor(post(urlMatching("/api/issues"))
                .withRequestBody(equalToJson("{\"title\": null}"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                )
        );

        HttpPost request = new HttpPost(wireMockURL + "/api/issues");

        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        String json = "{ \"title\": null}";
        StringEntity params = new StringEntity(json);
        request.setEntity(params);

        HttpResponse httpResponse = httpClient.execute(request);

        int responseStatusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("Response Status Code: " + responseStatusCode);

        assertEquals(200, responseStatusCode);
    }

    @Test
    public void getIssuesHttpNotOkay() throws IOException {
        System.out.println("getIssues A-Not-Okay");

        wireMockServer.stubFor(post(urlMatching("/api/issues"))
                .withRequestBody(equalToJson("{\"title\": null}"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                )
        );

        HttpPost request = new HttpPost(wireMockURL + "/api/issues");

        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        String json = "{ \"title\": null}";
        StringEntity params = new StringEntity(json);
        request.setEntity(params);

        HttpResponse httpResponse = httpClient.execute(request);

        int responseStatusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("Response Status Code: " + responseStatusCode);

        assertEquals(415, responseStatusCode);
    }

    @Test
    public void getIssueHttpOkay() throws IOException {

        System.out.println("getIssue A-Okay");

        wireMockServer.stubFor(get(urlMatching("/api/issues/58373"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                )
        );

        HttpGet request = new HttpGet(wireMockURL + "/api/issues/58373");

        HttpResponse httpResponse = httpClient.execute(request);

        int responseStatusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("Response Status Code: " + responseStatusCode);

        assertEquals(200, responseStatusCode);
    }

    @Test
    public void getIssueHttpNotOkay() throws IOException {
        System.out.println("getIssue A-Not-Okay");

        wireMockServer.stubFor(get(urlMatching("/api/issues/58373"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                )
        );

        HttpGet request = new HttpGet(wireMockURL + "/api/issues/58373");

        HttpResponse httpResponse = httpClient.execute(request);

        int responseStatusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("Response Status Code: " + responseStatusCode);

        assertEquals(415, responseStatusCode);
    }
}
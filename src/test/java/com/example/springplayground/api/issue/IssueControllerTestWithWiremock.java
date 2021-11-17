package com.example.springplayground.api.issue;

import com.example.springplayground.SpringPlaygroundApplication;
import com.example.springplayground.controller.model.IssueDTO;
import com.example.springplayground.controller.model.SearchIssues;
import com.example.springplayground.setup.TestApplicationConfiguration;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@ActiveProfiles("test")
@ContextConfiguration(classes = {SpringPlaygroundApplication.class, TestApplicationConfiguration.class})
@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class IssueControllerTestWithWiremock {

    @Autowired // Only acceptable in tests
    private WireMockServer wireMockServer;

    @Autowired
    private IssueController issueController;

    @Autowired
    @Qualifier("retrofitServiceEnv")
    private String wireMockURL;

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Before
    public void setup() {
        wireMockServer.resetMappings();
        wireMockServer.resetRequests();
        wireMockServer.resetToDefaultMappings();
    }

    @Test
    public void getIssuesHttpOkay() throws IOException {

        // Call a fake server to return me a hard-coded response
        wireMockServer.stubFor(post(urlMatching("/api/issues"))
                .withRequestBody(equalToJson("{\"title\": null}"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBodyFile("src/test/resources/__files/issue_test.json")
                )
        );


        /**
         * TODO:
         *  I am unable to call the service directly -> "use a service wrapper that uses a service bean of which that service bean is pointing to the wiremock serverl."
         */
        SearchIssues searchIssues = new SearchIssues();
        ResponseEntity<List<IssueDTO>> issues = issueController.getIssues(searchIssues);

        System.out.println(issues);


        // HttpPost request = new HttpPost(wireMockURL + "/api/issues");
        //
        // request.setHeader("Accept", "application/json");
        // request.setHeader("Content-type", "application/json");
        //
        // String json = "{ \"title\": null}";
        // StringEntity params = new StringEntity(json);
        // request.setEntity(params);
        //
        // HttpResponse httpResponse = httpClient.execute(request);
        //
        // int responseStatusCode = httpResponse.getStatusLine().getStatusCode();
        // System.out.println("Response Status Code: " + responseStatusCode);

        // String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        // System.out.println(responseString);
        //
        // assertEquals(200, responseStatusCode);
    }

    // @Test
    // public void getIssuesHttpNotOkay() throws IOException {
    //     System.out.println("getIssues A-Not-Okay");
    //
    //     wireMockServer.stubFor(post(urlMatching("/api/issues"))
    //             .withRequestBody(equalToJson("{\"title\": null}"))
    //             .willReturn(aResponse()
    //                     .withStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
    //             )
    //     );
    //
    //     HttpPost request = new HttpPost(wireMockURL + "/api/issues");
    //
    //     request.setHeader("Accept", "application/json");
    //     request.setHeader("Content-type", "application/json");
    //
    //     String json = "{ \"title\": null}";
    //     StringEntity params = new StringEntity(json);
    //     request.setEntity(params);
    //
    //     HttpResponse httpResponse = httpClient.execute(request);
    //
    //     int responseStatusCode = httpResponse.getStatusLine().getStatusCode();
    //     System.out.println("Response Status Code: " + responseStatusCode);
    //
    //     assertEquals(415, responseStatusCode);
    // }
    //
    // @Test
    // public void getIssueHttpOkay() throws IOException {
    //
    //     System.out.println("getIssue A-Okay");
    //
    //     wireMockServer.stubFor(get(urlMatching("/api/issues/58373"))
    //             .willReturn(aResponse()
    //                     .withStatus(HttpStatus.OK.value())
    //             )
    //     );
    //
    //     HttpGet request = new HttpGet(wireMockURL + "/api/issues/58373");
    //
    //     HttpResponse httpResponse = httpClient.execute(request);
    //
    //     int responseStatusCode = httpResponse.getStatusLine().getStatusCode();
    //     System.out.println("Response Status Code: " + responseStatusCode);
    //
    //     assertEquals(200, responseStatusCode);
    // }
    //
    // @Test
    // public void getIssueHttpNotOkay() throws IOException {
    //     System.out.println("getIssue A-Not-Okay");
    //
    //     wireMockServer.stubFor(get(urlMatching("/api/issues/58373"))
    //             .willReturn(aResponse()
    //                     .withStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
    //             )
    //     );
    //
    //     HttpGet request = new HttpGet(wireMockURL + "/api/issues/58373");
    //
    //     HttpResponse httpResponse = httpClient.execute(request);
    //
    //     int responseStatusCode = httpResponse.getStatusLine().getStatusCode();
    //     System.out.println("Response Status Code: " + responseStatusCode);
    //
    //     assertEquals(415, responseStatusCode);
    // }
}
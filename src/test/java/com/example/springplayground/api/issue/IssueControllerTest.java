package com.example.springplayground.api.issue;

import com.example.springplayground.SpringPlaygroundApplication;
import com.example.springplayground.util.ApplicationConfiguration;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
// TODO: 11/4/2021 Create TestApplicationConfiguration class - e.g. host bean & includes normal configuration

@ActiveProfiles("test")
@ContextConfiguration(classes = {SpringPlaygroundApplication.class, ApplicationConfiguration.class})
@RunWith(value = SpringJUnit4ClassRunner.class)
@WebAppConfiguration
class IssueControllerTest {

    private IssueController issueControllerTest;

    @Autowired // Only acceptable in tests
    private IssueService issueService;

    // @Autowired
    // @Qualifier("wireMockServer")
    // private WireMockServer wireMockServer;
    // @InjectMocks
    // private RetrofitService retrofitService;

    private final WireMockServer wireMockServer = new WireMockServer(
            new WireMockConfiguration().port(8080)
    );

    @BeforeEach
    public void before() {
        wireMockServer.start();
    }

    @Test
    public void contextLoads() throws Exception {
        /**
         * TODO:
         * 1) Create a Wiremock service - how much info should there be in the service?
         * 2) Call 'getIssues' endpoint - Lordie, lordie lord
         * 3) Validate successful response
         */

        // SearchIssues searchIssues = new SearchIssues();
        // ResponseEntity<List<IssueDTO>> result = issueControllerTest.getIssues(searchIssues);
        //
        // assertThat(result.getStatusCode().toString().equals("200"));


        wireMockServer.stubFor(WireMock.post(urlMatching("/api/issues"))
                .withRequestBody(equalToJson("title = null"))
                .willReturn(aResponse()
                        .withStatus(200))
        );

        // // given
        // int numberOne = 20;
        // int numberTwo = 30;
        //
        // // when
        // int result = numberOne + numberTwo;
        //
        // // then
        // int expected = 50;
        // assertThat(result).isEqualTo(expected);
    }
}
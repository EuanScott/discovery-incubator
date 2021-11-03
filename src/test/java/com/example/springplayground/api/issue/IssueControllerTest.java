package com.example.springplayground.api.issue;

import com.example.springplayground.controller.model.IssueDTO;
import com.example.springplayground.controller.model.SearchIssues;
import com.example.springplayground.setup.WireMockService;
import org.apache.http.impl.conn.Wire;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PreDestroy;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@TestConfiguration
class IssueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // @Autowired
    private IssueController issueControllerTest;

    @Autowired
    private IssueService issueService;

    // @Autowired
    // private WireMockService wireMockService;

    // @Before
    // public void setup() {
    //     this.mockMvc = MockMvcBuilders.standaloneSetup(issueControllerTest).build();
    // }

    IssueControllerTest(IssueController issueController) {
        WireMockService.beforeAll();
        this.issueControllerTest = issueController;
    }

    @Test
    public void contextLoads() throws Exception {
        /**
         * TODO:
         * 1) Create a Wiremock service - how much info should there be in the service?
         * 2) Call 'getIssues' endpoint - Lordie, lordie lord
         * 3) Validate successful response
         */


        IssueDTO issue = new IssueDTO();

        System.out.println(issue);

        // "this.issueControllerTest" is null
        SearchIssues searchIssues = new SearchIssues();
        ResponseEntity<List<IssueDTO>> result = issueControllerTest.getIssues(searchIssues);

        assertThat(result.getStatusCode().toString().equals("200"));

        // mockMvc.perform(
        //                 post("api/issues")
        //                         .contentType(MediaType.APPLICATION_JSON)
        //                         .content(mapper.writeValueAsString(req))
        //         )
        //         .andDo((print()))
        //         .andExpect(status().isOk());

        // WireMockService.stubFor(get(urlEqualTo("/some/thing"))
        //         .willReturn(aResponse()
        //                 .withHeader("Content-Type", "text/plain")
        //                 .withBody("Hello world!")));
        //
        // assertThat(testClient.get("/some/thing").statusCode(), is(200));
        // assertThat(testClient.get("/some/thing/else").statusCode(), is(404));


        // given
        // int numberOne = 20;
        // int numberTwo = 30;
        //
        // // when
        // int result = numberOne + numberTwo;
        //
        // // then
        // int expected = 50;
        // assertThat(result).isEqualTo(expected);

        WireMockService.afterAll();
    }
}
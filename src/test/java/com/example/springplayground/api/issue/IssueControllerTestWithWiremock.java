package com.example.springplayground.api.issue;

import com.example.springplayground.SpringPlaygroundApplication;
import com.example.springplayground.controller.model.IssueDTO;
import com.example.springplayground.controller.model.SearchIssues;
import com.example.springplayground.setup.TestApplicationConfiguration;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Before
    public void setup() {
        wireMockServer.resetMappings();
        wireMockServer.resetRequests();
        wireMockServer.resetToDefaultMappings();
    }

    @Test
    public void getIssuesWithoutSearchTerm() {

        // Create a fake server to return me a hard-coded response from the Down Stream
        wireMockServer.stubFor(get(urlEqualTo("/Issues"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBodyFile("issue_test.json")
                )
        );

        // Call the issue controller that will be using my fake server to return me the pre-defined response body
        SearchIssues searchIssues = new SearchIssues();
        ResponseEntity<List<IssueDTO>> issuesResponse = issueController.getIssues(searchIssues);

        // Base check: Make sure that the response object exists
        assertNotNull(issuesResponse);

        // Make sure that the amount of Issues being returned is the correct amount
        List<IssueDTO> issues = issuesResponse.getBody();
        assertNotNull(issues);
        assertEquals(2, issues.size());

        // Check that the Issue response being returned has the correct response that I have mocked out earlier
        IssueDTO issue = issues.get(0);
        assertNotNull(issue);
        assertEquals(BigDecimal.valueOf(58758), issue.getId());
        assertEquals("Moon Girl and Devil Dinosaur (2015) #5 (Guerra Wop Variant)", issue.getTitle());
    }

    @Test
    public void getIssuesWithSearchTerm() {

        wireMockServer.stubFor(get(urlEqualTo("/Issues"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBodyFile("issue_test.json")
                )
        );

        SearchIssues searchIssues = new SearchIssues();
        searchIssues.setTitle("marvel universe");
        ResponseEntity<List<IssueDTO>> issuesResponse = issueController.getIssues(searchIssues);

        assertNotNull(issuesResponse);
        List<IssueDTO> issues = issuesResponse.getBody();
        assertNotNull(issues);
        assertEquals(1, issues.size());
        IssueDTO issue = issues.get(0);
        assertNotNull(issue);
        assertEquals(BigDecimal.valueOf(54781), issue.getId());
        assertEquals("All-New, All-Different Marvel Universe (2015) #1", issue.getTitle());
    }
}
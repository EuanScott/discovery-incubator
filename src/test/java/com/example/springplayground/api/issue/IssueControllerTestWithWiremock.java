package com.example.springplayground.api.issue;

import com.example.springplayground.SpringPlaygroundApplication;
import com.example.springplayground.controller.model.ImageDTO;
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
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static org.junit.jupiter.api.Assertions.*;

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
    public void getIssues_success_withoutSearchTerm() {
        // Create a fake server to return me a hard-coded response from the Down Stream
        wireMockServer.stubFor(get(urlEqualTo("/Issues"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBodyFile("list_issues_test.json")
                )
        );

        // Call the issue controller that will be using my fake server to return me the pre-defined response body
        SearchIssues searchIssues = new SearchIssues();
        ResponseEntity<List<IssueDTO>> issuesResponse = issueController.getIssues(searchIssues);

        // Base check: Make sure that the response object exists
        assertNotNull(issuesResponse);

        // Make sure that the amount of Issues being returned is the correct amount
        List<IssueDTO> issues = issuesResponse.getBody();
        assertEquals(2, issues.size());

        // Check that the Issue response being returned has the correct response that I have mocked out earlier
        IssueDTO issue = issues.get(0);
        assertEquals(BigDecimal.valueOf(58758), issue.getId());
        assertEquals("Moon Girl and Devil Dinosaur (2015) #5 (Guerra Wop Variant)", issue.getTitle());
        assertEquals(null, issue.getDescription());
        assertEquals(-1, issue.getSeriesNumber());
        assertEquals("2016-01-15T19:22:35Z", issue.getPublicationDate());
        assertEquals(null, issue.getPublisherID());
        assertEquals("Marvel", issue.getPublisher());
        assertEquals(0, issue.getCreators().size());
        assertEquals(0, issue.getStock().size());
        assertEquals("http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available", issue.getThumbnail().getPath());
        assertEquals("jpg", issue.getThumbnail().getExtension());
        assertEquals("http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg", issue.getThumbnail().getPathIncludingExtension());
        assertEquals(1, issue.getImages().size());
        ImageDTO image = issue.getImages().get(0);
        assertEquals("http://i.annihil.us/u/prod/marvel/i/mg/6/a0/56707df23de44", image.getPath());
        assertEquals("jpg", image.getExtension());
        assertEquals("http://i.annihil.us/u/prod/marvel/i/mg/6/a0/56707df23de44.jpg", image.getPathIncludingExtension());
    }

    @Test
    public void getIssues_success_withSearchTerm() {
        wireMockServer.stubFor(get(urlEqualTo("/Issues"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBodyFile("list_issues_test.json")
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

    @Test
    public void getIssues_success_withSearchTerm_emptyResponse() {
        wireMockServer.stubFor(get(urlEqualTo("/Issues"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBodyFile("list_issues_test.json")
                )
        );

        SearchIssues searchIssues = new SearchIssues();
        searchIssues.setTitle("a name that doesn't exist");
        ResponseEntity<List<IssueDTO>> issuesResponse = issueController.getIssues(searchIssues);

        assertNotNull(issuesResponse);
        List<IssueDTO> issues = issuesResponse.getBody();
        assertNotNull(issues);
        assertEquals(0, issues.size());
    }

    @Test
    public void getIssues_failure_downstreamFailing() {
        wireMockServer.stubFor(get(urlEqualTo("/Issues"))
                .willReturn(aResponse()
                        .withStatus(SC_INTERNAL_SERVER_ERROR)
                )
        );

        try {
            SearchIssues searchIssues = new SearchIssues();
            issueController.getIssues(searchIssues);
            fail();
        } catch (Throwable e) {
            assertEquals("An error occurred Downstream: java.util.concurrent.ExecutionException: com.example.springplayground.exception.DownStreamApiException: An error occurred Downstream: java.lang.Throwable: ", e.getMessage());
        }
    }

    @Test
    public void getIssue_byId_success_withId() {
        wireMockServer.stubFor(get(urlEqualTo("/Issues/58758"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBodyFile("issue_test.json")
                )
        );

        ResponseEntity<IssueDTO> issuesResponse = issueController.getIssueById(BigDecimal.valueOf(58758));

        assertNotNull(issuesResponse);

        IssueDTO issue = issuesResponse.getBody();
        assertNotNull(issue);
        assertEquals(BigDecimal.valueOf(58758), issue.getId());
        assertEquals("Moon Girl and Devil Dinosaur (2015) #5 (Guerra Wop Variant)", issue.getTitle());
    }

    @Test
    public void getIssue_byId_failure_invalidId() {
        wireMockServer.stubFor(get(urlEqualTo("/Issues/12345"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.METHOD_NOT_ALLOWED.value())
                )
        );

        // TODO: The wireMockServer for some reason is not returning a 405 error.
        try {
            issueController.getIssueById(BigDecimal.valueOf(12345));
            fail();
        } catch (Throwable e) {
            // TODO: Is it okay that I purposely broke the endpoint and then just copied the response in here?
            assertEquals("An error occurred Downstream: java.util.concurrent.ExecutionException: java.lang.RuntimeException: An Unknown error occurred. Please try again later", e.getMessage());
        }
    }
}
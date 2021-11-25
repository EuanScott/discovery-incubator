package com.example.springplayground.api.issue;

import com.example.springplayground.SpringPlaygroundApplication;
import com.example.springplayground.controller.model.ImageDTO;
import com.example.springplayground.controller.model.IssueDTO;
import com.example.springplayground.controller.model.SearchIssues;
import com.example.springplayground.service.model.Issue;
import com.example.springplayground.setup.TestApplicationConfiguration;
import com.example.springplayground.util.ExecutorServiceConfiguration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {SpringPlaygroundApplication.class, TestApplicationConfiguration.class})
@SpringBootTest
@WebAppConfiguration
public class IssueControllerTestsWithMockito {

    @Mock
    IssueService issueService;

    @InjectMocks
    ExecutorServiceConfiguration executorService;

    IssueController issueController;

    Gson gson = new Gson();

    @Before
    public void setup() {
        issueController = new IssueController(
                issueService,
                executorService.fixedThreadPool(),
                executorService.multiThreadedExecutorServiceTimeout(),
                executorService.multiThreadedExecutorServiceTimeoutType()
        );
    }

    @Test
    public void getIssues_success_withoutSearchTerm() throws FileNotFoundException {
        // mock out the issue service
        List<Issue> customResponse = getListOfIssuesFromTestJson();

        when(issueService.getIssues()).thenReturn(customResponse);

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
    public void getIssues_success_withSearchTerm() throws FileNotFoundException {
        List<Issue> customResponse = getListOfIssuesFromTestJson();

        when(issueService.getIssues()).thenReturn(customResponse);

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
    public void getIssues_success_withSearchTerm_emptyResponse() throws FileNotFoundException {
        List<Issue> customResponse = getListOfIssuesFromTestJson();

        when(issueService.getIssues()).thenReturn(customResponse);

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
        when(issueService.getIssues()).thenThrow(new RuntimeException("An Unknown error occurred. Please try again later"));

        try {
            SearchIssues searchIssues = new SearchIssues();
            issueController.getIssues(searchIssues);
            // forcing the test to fail if execution reaches this point
            fail();
        } catch (RuntimeException rte) {
            assertEquals("An error occurred Downstream: java.util.concurrent.ExecutionException: java.lang.RuntimeException: An Unknown error occurred. Please try again later", rte.getMessage());
        } catch (Throwable e) {
            fail();
        }
    }

    @Test
    public void getIssue_byId_success_withId() throws FileNotFoundException {
        Issue customResponse = getSingleIssuesFromTestJson();

        when(issueService.getIssue(BigDecimal.valueOf(58758))).thenReturn(customResponse);

        ResponseEntity<IssueDTO> issuesResponse = issueController.getIssueById(BigDecimal.valueOf(58758));

        assertNotNull(issuesResponse);

        IssueDTO issue = issuesResponse.getBody();
        assertNotNull(issue);
        assertEquals(BigDecimal.valueOf(58758), issue.getId());
        assertEquals("Moon Girl and Devil Dinosaur (2015) #5 (Guerra Wop Variant)", issue.getTitle());
    }

    @Test
    public void getIssue_byId_failure_invalidId() {
        when(issueService.getIssue(BigDecimal.valueOf(12345))).thenThrow(new RuntimeException("An Unknown error occurred. Please try again later"));

        try {
            issueService.getIssue(BigDecimal.valueOf(12345));
            fail();
        } catch (Throwable e) {
            assertEquals("An Unknown error occurred. Please try again later", e.getMessage());
        }
    }

    //#region Helpers

    private List<Issue> getListOfIssuesFromTestJson() throws FileNotFoundException {
        // Make sure GSON is the same used as in the actual code
        JsonReader reader = new JsonReader(new FileReader("src/test/resources/__files/list_issues_test.json"));
        return gson.fromJson(reader, new TypeToken<List<Issue>>() {
        }.getType());
    }

    private Issue getSingleIssuesFromTestJson() throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader("src/test/resources/__files/issue_test.json"));
        return gson.fromJson(reader, new TypeToken<Issue>() {
        }.getType());
    }

    //#endregion
}

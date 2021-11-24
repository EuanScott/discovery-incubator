package com.example.springplayground.api.issue;

import com.example.springplayground.SpringPlaygroundApplication;
import com.example.springplayground.controller.model.IssueDTO;
import com.example.springplayground.controller.model.SearchIssues;
import com.example.springplayground.service.model.Issue;
import com.example.springplayground.setup.TestApplicationConfiguration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

    ExecutorService executorService;
    Long executorServiceTimeout;
    TimeUnit executorServiceTimeoutType;

    IssueController issueController;

    Gson gson = new Gson();

    @Before
    public void setup() {
        executorService = Executors.newFixedThreadPool(7);
        executorServiceTimeoutType = TimeUnit.MILLISECONDS;
        executorServiceTimeout = 10000L;
        issueController = new IssueController(issueService, executorService, executorServiceTimeout, executorServiceTimeoutType);
    }

    @Test
    public void mockGetIssuesWithoutSearchTerm() throws FileNotFoundException {
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
        assertNotNull(issues);
        assertEquals(2, issues.size());

        // Check that the Issue response being returned has the correct response that I have mocked out earlier
        IssueDTO issue = issues.get(0);
        assertNotNull(issue);
        assertEquals(BigDecimal.valueOf(58758), issue.getId());
        assertEquals("Moon Girl and Devil Dinosaur (2015) #5 (Guerra Wop Variant)", issue.getTitle());
    }

    @Test
    public void mockGetIssuesWithSearchTerm() throws FileNotFoundException {
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
    public void getIssuesWithSearchTermResultEmpty() throws FileNotFoundException {
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
    public void mockGetIssuesDownstreamFailResponse() {
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
    public void getIssueByIdWithId() throws FileNotFoundException {
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
    public void getIssueByIdWithAnInvalidId() {
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

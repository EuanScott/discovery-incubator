package com.example.springplayground.api.issue;

import com.example.springplayground.controller.model.IssueDTO;
import com.example.springplayground.controller.model.SearchIssues;
import com.example.springplayground.service.model.*;
import com.google.gson.Gson;
import io.micrometer.core.instrument.search.Search;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IssueControllerMocks {

    // @Rule
    // public MockitoRule initRule = MockitoJUnit.rule();

    @Mock
    IssueService issueService;
    @Mock
    IssueController issueController;

    // @Mock
    // List<Issue> issueList;

    /**
     * TODO:
     *  How to get the thenReturn to return a custom JSON response that I create?
     *  Do I test the issueService or the issueController?
     *      -> I feel I should be calling the controller directly as that is what the user would interact with and not care about the service
     */
    @Test
    public void mockGetIssue() {
        System.out.println("Doing Mockito");

        List<IssueDTO> customResponse = Arrays.asList(new IssueDTO(), new IssueDTO());
        System.out.println("customResponse: " + customResponse);

        SearchIssues searchIssues = new SearchIssues();
        ResponseEntity<List<IssueDTO>> apiResponse = issueController.getIssues(searchIssues);
        System.out.println("apiResponse: " + apiResponse);

        when(issueController.getIssues(searchIssues)).thenReturn((ResponseEntity<List<IssueDTO>>) customResponse);

        assertEquals(issueService.getIssues(), apiResponse);
    }
}

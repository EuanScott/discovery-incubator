package com.example.springplayground.api.issue;

import com.example.springplayground.SpringPlaygroundApplication;
import com.example.springplayground.controller.model.IssueDTO;
import com.example.springplayground.controller.model.SearchIssues;
import com.example.springplayground.setup.TestApplicationConfiguration;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {SpringPlaygroundApplication.class, TestApplicationConfiguration.class})
@SpringBootTest
@WebAppConfiguration
public class IssueControllerMocks {

    // @Rule
    // public MockitoRule initRule = MockitoJUnit.rule();

    // @Mock
    // IssueService issueService;

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
    public void mockGetIssues() {
        SearchIssues searchIssues = new SearchIssues();
        ResponseEntity<List<IssueDTO>> customResponse = ResponseEntity.ok(Arrays.asList(new IssueDTO(), new IssueDTO()));

        when(issueController.getIssues(searchIssues)).thenReturn(customResponse);

        assertEquals(customResponse, issueController.getIssues(searchIssues));
    }

    // @Test
    // public void mockGetIssue() throws FileNotFoundException, ParseException {
    //     JSONParser parser = new JSONParser();
    //     Object jsonObject = parser.parse(new FileReader("C:/Users/p01euans/Desktop/list_test.json"));
    //     IssueDTO myObj = (IssueDTO)jsonObject;
    //     System.out.println(myObj);
    //
    //     ResponseEntity<IssueDTO> customResponse = ResponseEntity.ok(myObj);
    //
    //     when(issueController.getIssueById(BigDecimal.valueOf(42))).thenReturn(customResponse);
    //
    //     assertThat(issueController.getIssueById(BigDecimal.valueOf(42))).hasFieldOrProperty("title");
    //
    //     assertEquals(customResponse, issueController.getIssueById(BigDecimal.valueOf(42)));
    // }
}

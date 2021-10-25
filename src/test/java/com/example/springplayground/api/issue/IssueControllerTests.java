package com.example.springplayground.api.issue;

import com.example.springplayground.SpringPlaygroundApplication;
import com.example.springplayground.controller.model.IssueDTO;
import com.example.springplayground.controller.model.SearchIssues;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest
@WebMvcTest(IssueController.class)
@ContextConfiguration(classes = SpringPlaygroundApplication.class)
@ActiveProfiles({"test"})
class IssueControllerTests {

    @Autowired
    private IssueService issueService;

    @Test
    @DisplayName("List Issue 200 OK")
    void testIssueList() {
        // given
        IssueController issueController = new IssueController(issueService);
        SearchIssues searchIssues = new SearchIssues();

        // when
        ResponseEntity<List<IssueDTO>> testIssues = issueController.getIssues(searchIssues);

        // then
        Assertions.assertEquals("200", testIssues.getStatusCode().toString());
    }
}
package com.example.springplayground.api.issue;

import com.example.springplayground.SpringPlaygroundApplication;
import com.example.springplayground.controller.model.IssueDTO;
import com.example.springplayground.controller.model.SearchIssues;
import com.example.springplayground.service.model.Issue;
import com.example.springplayground.setup.TestApplicationConfiguration;
import com.example.springplayground.util.MapperUtil;
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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {SpringPlaygroundApplication.class, TestApplicationConfiguration.class})
@SpringBootTest
@WebAppConfiguration
public class IssueControllerTestsWithMockito {

    @Mock
    IssueService issueService;

    @Mock
    IssueController issueController;

    @Test
    public void mockGetIssues() {
        // given
        List<Issue> customResponse = Arrays.asList(new Issue(), new Issue());
        when(issueService.getIssues()).thenReturn(customResponse);

        // when
        List<IssueDTO> mappedIssues;
        mappedIssues = MapperUtil.mapList(issueService.getIssues(), IssueDTO.class);

        // then
        assertEquals(Arrays.asList(new IssueDTO(), new IssueDTO()), mappedIssues);
    }

    @Test
    public void mockGetIssue() {
        // given
        List<Issue> customResponse = Arrays.asList(new Issue(), new Issue());
        when(issueService.getIssues()).thenReturn(customResponse);

        // when
        List<IssueDTO> mappedIssues;
        mappedIssues = MapperUtil.mapList(issueService.getIssues(), IssueDTO.class);

        List<IssueDTO> comicIssues;
        String issueName = "";
        comicIssues = mappedIssues
                .parallelStream()
                .filter(issue -> {
                    String issueTitle = (issue.getTitle() == null) ? "" : issue.getTitle().toLowerCase();
                    return issueTitle.contains(issueName);
                })
                .collect(Collectors.toList());

        // then
        assertEquals(Arrays.asList(new IssueDTO(), new IssueDTO()), comicIssues);
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

package com.example.springplayground.api.issue;

import com.example.springplayground.controller.api.IssuesApi;
import com.example.springplayground.controller.model.IssueDTO;
import com.example.springplayground.service.model.Issue;
import com.example.springplayground.util.MapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api")
public class IssueController implements IssuesApi {

    private final IssueService issueService;

    private final List<IssueDTO> issuesGroup = new ArrayList<>();

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    // @Override
    // public ResponseEntity<IssueDTO> getIssueById(BigDecimal id) {
    //     Issue issue = issueService.getIssue(id);
    //     return ResponseEntity.ok(MapperUtil.mapObject(issue, IssueDTO.class));
    // }

    @Override
    public ResponseEntity<List<IssueDTO>> getIssueByName(String searchTerm) {

        String issueName = searchTerm.toLowerCase();

        List<Issue> issues = issueService.getIssues();
        List<IssueDTO> mappedIssues = MapperUtil.mapList(issues, IssueDTO.class);

        List<IssueDTO> result = mappedIssues.stream().filter(issue -> {
                    String title = issue.getTitle().toLowerCase();

                    return title.contains(issueName);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<IssueDTO>> getIssueList() {

        int iterations = 5;

        if (!issuesGroup.isEmpty()) issuesGroup.clear();

        List<Issue> issues = issueService.getIssues();
        List<IssueDTO> mappedIssues = MapperUtil.mapList(issues, IssueDTO.class);

        IntStream.range(0, iterations)
                .forEach(index -> issuesGroup.addAll(mappedIssues));

        return ResponseEntity.ok(issuesGroup);
    }
}
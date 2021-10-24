package com.example.springplayground.api.issue;

import com.example.springplayground.controller.api.IssuesApi;
import com.example.springplayground.controller.model.IssueDTO;
import com.example.springplayground.controller.model.SearchIssues;
import com.example.springplayground.service.model.Issue;
import com.example.springplayground.util.MapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api")
public class IssueController implements IssuesApi {

    private final IssueService issueService;

    private List<IssueDTO> comicIssues = new ArrayList<>();
    private List<IssueDTO> mappedIssues;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @Override
    public ResponseEntity<IssueDTO> getIssueById(BigDecimal id) {

        Future<Issue> futureIssue = issueService.getIssue(id);
        Issue issue = new Issue();

        try {
            issue = futureIssue.get();
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(MapperUtil.mapObject(issue, IssueDTO.class));
    }

    @Override
    public ResponseEntity<List<IssueDTO>> getIssues(SearchIssues searchIssues) {

        List<Issue> issues = new ArrayList<>();
        Future<List<Issue>> futureIssues = issueService.getIssues();

        try {
            issues = futureIssues.get();
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        mappedIssues = MapperUtil.mapList(issues, IssueDTO.class);

        if (!comicIssues.isEmpty())
            comicIssues.clear();

        if (searchIssues.getTitle() == null || Objects.equals(searchIssues.getTitle(), "")) {
            int iterations = 5;

            IntStream.range(0, iterations)
                    .forEach(index -> comicIssues.addAll(mappedIssues));
        } else {
            String issueName = searchIssues.getTitle().toLowerCase();

            comicIssues = mappedIssues
                    .parallelStream()
                    .filter(issue -> {
                        String issueTitle = issue.getTitle().toLowerCase();
                        return issueTitle.contains(issueName);
                    })
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(comicIssues);
    }
}
package com.example.springplayground.api.issue;

import com.example.springplayground.controller.api.IssuesApi;
import com.example.springplayground.controller.model.IssueDTO;
import com.example.springplayground.controller.model.SearchIssues;
import com.example.springplayground.exception.IssueException;
import com.example.springplayground.service.model.Issue;
import com.example.springplayground.util.MapperUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api")
public class IssueController implements IssuesApi {

    private final IssueService issueService;
    private final ExecutorService executorService;

    private List<IssueDTO> comicIssues = new ArrayList<>();
    private List<IssueDTO> mappedIssues;

    public IssueController(IssueService issueService, @Qualifier("multiThreaded") ExecutorService executorService) {
        this.issueService = issueService;
        this.executorService = executorService;
    }

    @Override
    public ResponseEntity<IssueDTO> getIssueById(BigDecimal id) {
        Issue issue;
        Future<Issue> futureIssue = executorService.submit(() -> {
            return issueService.getIssue(id);
        });

        try {
            issue = futureIssue.get(10000, TimeUnit.MILLISECONDS);
        }
        catch (ExecutionException | InterruptedException | TimeoutException | RuntimeException error) {
            executorService.shutdown();
            throw new IssueException("An unexpected error occurred: ", error);
        }

        return ResponseEntity.ok(MapperUtil.mapObject(issue, IssueDTO.class));
    }

    @Override
    public ResponseEntity<List<IssueDTO>> getIssues(SearchIssues searchIssues) {

        List<Issue> issues;
        Future<List<Issue>> futureIssues = executorService.submit(() -> {
            return issueService.getIssues();
        });

        try {
            issues = futureIssues.get(10000, TimeUnit.MILLISECONDS);
        }
        catch (ExecutionException | InterruptedException | TimeoutException | RuntimeException error) {
            executorService.shutdown();
            throw new IssueException("An unexpected error occurred: ", error);
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
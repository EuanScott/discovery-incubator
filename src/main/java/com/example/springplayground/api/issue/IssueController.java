package com.example.springplayground.api.issue;

import com.example.springplayground.controller.api.IssuesApi;
import com.example.springplayground.controller.model.IssueDTO;
import com.example.springplayground.controller.model.SearchIssues;
import com.example.springplayground.exception.DownStreamApiException;
import com.example.springplayground.service.model.Issue;
import com.example.springplayground.util.MapperUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class IssueController implements IssuesApi {

    private final IssueService issueService;
    private final ExecutorService executorService;
    private final Long executorServiceTimeout;
    private final TimeUnit executorServiceTimeoutType;

    private List<IssueDTO> mappedIssues;

    public IssueController(
            IssueService issueService,
            @Qualifier("multiThreadedExecutorService") ExecutorService executorService,
            @Qualifier("multiThreadedExecutorServiceTimeout") Long executorServiceTimeout,
            @Qualifier("multiThreadedExecutorServiceTimeoutType") TimeUnit executorServiceTimeoutType
    ) {
        this.issueService = issueService;
        this.executorService = executorService;
        this.executorServiceTimeout = executorServiceTimeout;
        this.executorServiceTimeoutType = executorServiceTimeoutType;
    }

    @Override
    public ResponseEntity<IssueDTO> getIssueById(BigDecimal id) {
        Issue issue;
        Future<Issue> futureIssue = executorService.submit(() -> issueService.getIssue(id));

        try {
            issue = futureIssue.get(executorServiceTimeout, executorServiceTimeoutType);
        } catch (ExecutionException | InterruptedException | TimeoutException | RuntimeException error) {
            futureIssue.cancel(false);
            throw new DownStreamApiException(error);
        }

        return ResponseEntity.ok(MapperUtil.mapObject(issue, IssueDTO.class));
    }

    @Override
    public ResponseEntity<List<IssueDTO>> getIssues(SearchIssues searchIssues) {

        List<Issue> issues;
        Future<List<Issue>> futureIssues = executorService.submit(() -> issueService.getIssues());

        try {
            issues = futureIssues.get(executorServiceTimeout, executorServiceTimeoutType);
        } catch (ExecutionException | InterruptedException | TimeoutException | RuntimeException error) {
            futureIssues.cancel(false);
            throw new DownStreamApiException(error);
        }

        mappedIssues = MapperUtil.mapList(issues, IssueDTO.class);
        List<IssueDTO> comicIssues;

        if (StringUtils.hasText(searchIssues.getTitle())) {
            String issueNameInLowercase = searchIssues.getTitle().toLowerCase();

            comicIssues = mappedIssues
                    .parallelStream()
                    .filter(issue -> issue.getTitle().toLowerCase().contains(issueNameInLowercase))
                    .collect(Collectors.toList());
        } else {
            // Used for when I want to return 30 comic issues
            // IntStream.range(0, 5)
            //         .forEach(index -> comicIssues.addAll(mappedIssues));
            comicIssues = mappedIssues;
        }

        return ResponseEntity.ok(comicIssues);
    }
}
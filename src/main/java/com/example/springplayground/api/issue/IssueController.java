package com.example.springplayground.api.issue;

import com.example.springplayground.controller.api.IssuesApi;
import com.example.springplayground.controller.model.IssueDTO;
import com.example.springplayground.controller.model.SearchIssues;
import com.example.springplayground.util.MapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
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
    public ResponseEntity<List<IssueDTO>> getIssues(SearchIssues searchIssues) {

        return ResponseEntity.ok(new ArrayList<>());

        // List<com.example.springplayground.service.model.Issue> issues = issueService.getIssues();
        // mappedIssues = MapperUtil.mapList(issues, IssueDTO.class);
        //
        // if (!comicIssues.isEmpty()) comicIssues.clear();
        //
        // if (searchIssues.getTitle() == null) {
        //     int iterations = 5;
        //
        //     IntStream.range(0, iterations)
        //             .forEach(index -> comicIssues.addAll(mappedIssues));
        // } else {
        //     String issueName = searchIssues.getTitle().toLowerCase();
        //
        //     comicIssues = mappedIssues
        //             .stream()
        //             .filter(issue -> {
        //                 String issueTitle = issue.getTitle().toLowerCase();
        //                 return issueTitle.contains(issueName);
        //             })
        //             .collect(Collectors.toList());
        // }
        //
        // return ResponseEntity.ok(comicIssues);
    }


    // @Override
    // public ResponseEntity<List<IssueDTO>> getIssueList() {
    //
    //     int iterations = 5;
    //
    //     if (!comicIssues.isEmpty()) comicIssues.clear();
    //
    //     List<Issue> issues = issueService.getIssues();
    //     List<IssueDTO> mappedIssues = MapperUtil.mapList(issues, IssueDTO.class);
    //
    //     IntStream.range(0, iterations)
    //             .forEach(index -> comicIssues.addAll(mappedIssues));
    //
    //     return ResponseEntity.ok(comicIssues);
    // }
}
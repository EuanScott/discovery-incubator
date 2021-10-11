package com.example.springplayground.api.issue;

import com.example.springplayground.controller.api.IssuesApi;
import com.example.springplayground.controller.model.IssueDTO;
import com.example.springplayground.service.model.Issue;
import com.example.springplayground.util.MapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class IssueController implements IssuesApi {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @Override
    public ResponseEntity<IssueDTO> getIssueById(BigDecimal id) {
        Issue issue = issueService.getIssue(id);
        return ResponseEntity.ok(MapperUtil.mapObject(issue, IssueDTO.class));
    }

    @Override
    public ResponseEntity<List<IssueDTO>> getIssueList() {
        List<Issue> issues = issueService.getIssues();
        List<IssueDTO> tester1 = MapperUtil.mapList(issues, IssueDTO.class);
        ResponseEntity<List<IssueDTO>> tester2 = ResponseEntity.ok(tester1);
        return tester2;
    }
}

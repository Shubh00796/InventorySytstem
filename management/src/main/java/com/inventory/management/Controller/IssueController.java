package com.inventory.management.Controller;

import com.inventory.management.Dtos.IssueDTO;
import com.inventory.management.ServiceImpl.IssueServiceImpl;
import com.inventory.management.service.IssueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
@Slf4j
public class IssueController {

    private final IssueService issueService;

    // Create an issue
    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@Valid @RequestBody IssueDTO issueDTO) {
        log.info("Creating new issue");
        IssueDTO createdIssue = issueService.createIssue(issueDTO);
        return ResponseEntity.ok(createdIssue);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueDTO> getIssueById(@PathVariable Long id) {
        log.info("Fetching issue with ID: {}", id);
        IssueDTO issue = issueService.getIssueById(id);
        return ResponseEntity.ok(issue);
    }

    // Update an issue
    @PutMapping("/{id}")
    public ResponseEntity<IssueDTO> updateIssue(@PathVariable Long id, @Valid @RequestBody IssueDTO issueDTO) {
        log.info("Updating issue with ID: {}", id);
        IssueDTO updatedIssue = issueService.updateIssue(id, issueDTO);
        return ResponseEntity.ok(updatedIssue);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<IssueDTO>> getIssuesByProjectId(@PathVariable Long projectId) {
        log.info("Fetching issues for project ID: {}", projectId);
        List<IssueDTO> issues = issueService.getIssuesByProjectId(projectId);
        return ResponseEntity.ok(issues);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        log.info("Deleting issue with ID: {}", id);
        issueService.deleteIssue(id);
        return ResponseEntity.noContent().build();
    }
}

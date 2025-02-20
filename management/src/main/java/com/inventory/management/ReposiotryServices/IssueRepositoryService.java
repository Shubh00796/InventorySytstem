package com.inventory.management.ReposiotryServices;

import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.Issue;
import com.inventory.management.Model.Project;
import com.inventory.management.Repo.IssueRepository;
import com.inventory.management.Repo.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class IssueRepositoryService {

    private final IssueRepository issueRepository;
    private final ProjectRepositoryService projectRepositoryService;


    public Issue getIssueFromDbById(Long id) {
        return issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project with ID " + id + " not found"));
    }

    public List<Issue> retrieveProjectById(Long projectId) {
        return issueRepository.findByProjectId(projectId);
    }

    public Issue saveIssue(Issue issue) {
        if(issue.getProjectId() == null || !projectRepositoryService.existsById(issue.getProjectId())){
            throw new ResourceNotFoundException("Cannot create issue. Project with ID " + issue.getProjectId() + " does not exist.");
        }

        return issueRepository.save(issue);
    }

    public void deleteProject(Long id) {
        Issue issue = getIssueFromDbById(id);
        issueRepository.delete(issue);
    }
}

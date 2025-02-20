package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.IssueDTO;
import com.inventory.management.Enums.IssueStatus;
import com.inventory.management.Mapper.IssueMapper;
import com.inventory.management.Model.Issue;
import com.inventory.management.ReposiotryServices.IssueRepositoryService;
import com.inventory.management.service.IssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {
    private final IssueRepositoryService repositoryService;
    private final IssueMapper mapper;

    @Override
    public IssueDTO createIssue(IssueDTO issueDTO) {
        Issue entity = mapper.toEntity(issueDTO);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setStatus(IssueStatus.PENDING);
        Issue issue = repositoryService.saveIssue(entity);
        return mapper.toDto(issue);
    }

    @Override
    public IssueDTO updateIssue(Long issueId, IssueDTO issueDTO) {
        log.info("Updating project with ID: {}", issueId);

        Issue existingIssue = repositoryService.getIssueFromDbById(issueId);
        if (existingIssue.getId() == null) {
            throw new IllegalStateException("Project ID cannot be null while updating.");
        }
        if (issueDTO.getStatus() != null && !existingIssue.getStatus().canTransitionTo(issueDTO.getStatus())) {
            throw new IllegalStateException("Invalid status transition from "
                    + existingIssue.getStatus() + " to " + issueDTO.getStatus());
        }

        mapper.updateEntityFromDto(issueDTO, existingIssue);
        existingIssue.setUpdatedAt(LocalDateTime.now());
        Issue issue = repositoryService.saveIssue(existingIssue);
        return mapper.toDto(issue);
    }

    @Override
    public IssueDTO getIssueById(Long issueId) {
        log.info("Fetching project with ID: {}", issueId);

        Issue issue = repositoryService.getIssueFromDbById(issueId);

        log.info("Project retrieved successfully with ID: {}", issueId);

        return mapper.toDto(issue);
    }

    @Override
    public List<IssueDTO> getIssuesByProjectId(Long projectId) {
        return repositoryService.retrieveProjectById(projectId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteIssue(Long id) {
        repositoryService.deleteProject(id);

    }
}

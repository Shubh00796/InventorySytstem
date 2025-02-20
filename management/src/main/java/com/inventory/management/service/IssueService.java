package com.inventory.management.service;

import com.inventory.management.Dtos.IssueDTO;

import java.util.List;

public interface IssueService {

    IssueDTO createIssue(IssueDTO issueDTO);

    IssueDTO updateIssue(Long issueId, IssueDTO issueDTO);

    IssueDTO getIssueById(Long issueId);


    List<IssueDTO> getIssuesByProjectId(Long projectId);

    void deleteIssue(Long id);
}

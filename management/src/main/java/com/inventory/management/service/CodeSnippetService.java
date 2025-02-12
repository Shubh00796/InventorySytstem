package com.inventory.management.service;

import com.inventory.management.Dtos.CodeSnippetDTO;

import java.util.List;

public interface CodeSnippetService {
    List<CodeSnippetDTO> getAllSnippets();
    CodeSnippetDTO getSnippetById(Long id);
    CodeSnippetDTO createSnippet(CodeSnippetDTO codeSnippetDTO);
    CodeSnippetDTO updateSnippet(Long id, CodeSnippetDTO codeSnippetDTO);
    void deleteSnippet(Long id);
}
package com.inventory.management.ReposiotryServices;

import com.inventory.management.Dtos.CodeSnippetDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.CodeSnippet;
import com.inventory.management.Repo.CodeSnippetRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeSnippetReposiotryService {
    private final CodeSnippetRepository codeSnippetRepository;


    public List<CodeSnippet> getAllCodeSnippets() {
        return codeSnippetRepository.findAll();
    }

    public Optional<CodeSnippet> getByCodeSnippetId(Long id) {
        return codeSnippetRepository.findById(id);
    }

    public CodeSnippet saveSnippet(CodeSnippet codeSnippet) {
        return codeSnippetRepository.save(codeSnippet);
    }

    public CodeSnippet updateCodeSnippet(Long id, CodeSnippetDTO codeSnippetDTO) {
        CodeSnippet codeSnippet = getCodeSnippet(id, codeSnippetDTO);

        return codeSnippetRepository.save(codeSnippet);
    }

    private CodeSnippet getCodeSnippet(Long id, CodeSnippetDTO codeSnippetDTO) {
        CodeSnippet codeSnippet = codeSnippetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Code for give id not found :" + id));

        Optional.ofNullable(codeSnippetDTO.getCode()).ifPresent(codeSnippet::setCode);
        Optional.ofNullable(codeSnippetDTO.getTitle()).ifPresent(codeSnippet::setTitle);
        Optional.ofNullable(codeSnippetDTO.getLanguage()).ifPresent(codeSnippet::setLanguage);
        Optional.ofNullable(codeSnippetDTO.getDescription()).ifPresent(codeSnippet::setDescription);
        return codeSnippet;
    }

    public void deleteCode(Long id) {
        CodeSnippet codeSnippet = codeSnippetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id not found" + id));

        codeSnippetRepository.delete(codeSnippet);
    }
}

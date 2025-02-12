package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.CodeSnippetDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Mapper.CodeSnippetMapper;
import com.inventory.management.Model.CodeSnippet;
import com.inventory.management.Repo.CodeSnippetRepository;
import com.inventory.management.ReposiotryServices.CodeSnippetReposiotryService;
import com.inventory.management.ValidatorLogics.CodeSnippetValidator;
import com.inventory.management.service.CodeSnippetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CodeSnippetServiceImpl implements CodeSnippetService {
    private final CodeSnippetMapper codeSnippetMapper;
    private final CodeSnippetReposiotryService codeSnippetReposiotryService;

    @Override
    public List<CodeSnippetDTO> getAllSnippets() {
        return codeSnippetReposiotryService.getAllCodeSnippets()
                .stream()
                .map(codeSnippetMapper::codeSnippetToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CodeSnippetDTO getSnippetById(Long id) {
        return codeSnippetReposiotryService.getByCodeSnippetId(id)
                .map(codeSnippetMapper::codeSnippetToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("NOT FOUND"));
    }

    @Override
    public CodeSnippetDTO createSnippet(CodeSnippetDTO codeSnippetDTO) {
        CodeSnippetValidator.validate(codeSnippetDTO);
        CodeSnippet codeSnippet = codeSnippetMapper.dtoToCodeSnippet(codeSnippetDTO);
        CodeSnippet savedCodeSnippetToRepo = codeSnippetReposiotryService.saveSnippet(codeSnippet);

        return codeSnippetMapper.codeSnippetToDTO(savedCodeSnippetToRepo);
    }

    @Override
    public CodeSnippetDTO updateSnippet(Long id, CodeSnippetDTO codeSnippetDTO) {
        CodeSnippetValidator.validate(codeSnippetDTO);
        CodeSnippet updatedCodeSnippet = codeSnippetReposiotryService.updateCodeSnippet(id, codeSnippetDTO);
        return codeSnippetMapper.codeSnippetToDTO(updatedCodeSnippet);
    }

    @Override
    public void deleteSnippet(Long id) {
        codeSnippetReposiotryService.deleteCode(id);

    }
}

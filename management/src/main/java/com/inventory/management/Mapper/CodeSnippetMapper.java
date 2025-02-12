package com.inventory.management.Mapper;

import com.inventory.management.Dtos.CodeSnippetDTO;
import com.inventory.management.Model.CodeSnippet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CodeSnippetMapper {
    CodeSnippetDTO codeSnippetToDTO(CodeSnippet codeSnippet);
    CodeSnippet dtoToCodeSnippet(CodeSnippetDTO codeSnippetDTO);
}
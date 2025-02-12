package com.inventory.management.Controller;

import com.inventory.management.Dtos.CodeSnippetDTO;
import com.inventory.management.service.CodeSnippetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/snippets")
@RequiredArgsConstructor
public class CodeSnippetController {

    private final CodeSnippetService codeSnippetService;

    @GetMapping
    public ResponseEntity<List<CodeSnippetDTO>> getAllSnippets() {
        return ResponseEntity.ok(codeSnippetService.getAllSnippets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CodeSnippetDTO> getSnippetById(@PathVariable Long id) {
        return ResponseEntity.ok(codeSnippetService.getSnippetById(id));
    }

    @PostMapping
    public ResponseEntity<CodeSnippetDTO> createSnippet(@RequestBody CodeSnippetDTO codeSnippetDTO) {
        return ResponseEntity.ok(codeSnippetService.createSnippet(codeSnippetDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CodeSnippetDTO> updateSnippet(@PathVariable Long id, @RequestBody CodeSnippetDTO codeSnippetDTO) {
        return ResponseEntity.ok(codeSnippetService.updateSnippet(id, codeSnippetDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSnippet(@PathVariable Long id) {
        codeSnippetService.deleteSnippet(id);
        return ResponseEntity.noContent().build();
    }
}

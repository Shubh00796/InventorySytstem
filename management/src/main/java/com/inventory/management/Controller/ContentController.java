package com.inventory.management.Controller;

import com.inventory.management.Dtos.ContentDTO;
import com.inventory.management.Factories.ContentFactory;
import com.inventory.management.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/content")
@Tag(name = "Content Management", description = "APIs for managing content")
public class ContentController {

    private final ContentService contentService;
    private final ContentFactory contentFactory;

    public ContentController(@Qualifier("articleFactory") ContentFactory contentFactory, ContentService contentService) {
        this.contentFactory = contentFactory;
        this.contentService = contentService;
    }

    @PostMapping
    public ResponseEntity<ContentDTO> createContent(
            @RequestParam String id,
            @RequestParam String title,
            @RequestParam String description) {
        ContentDTO contentDTO = contentService.createContent(contentFactory, id, title, description);
        return ResponseEntity.ok(contentDTO);
    }

    @GetMapping
    @Operation(summary = "Get All Content", description = "Fetch all content entries.")
    public ResponseEntity<List<ContentDTO>> getAllContent() {
        return ResponseEntity.ok(contentService.getAllContent());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Content by ID", description = "Fetches content details by its ID.")
    public ResponseEntity<ContentDTO> getContentById(@PathVariable String id) {
        return ResponseEntity.ok(contentService.getContentById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Content", description = "Deletes a content entry by ID.")
    public ResponseEntity<Void> deleteContent(@PathVariable String id) {
        contentService.deleteContent(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}

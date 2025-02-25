package com.inventory.management.Controller;

import com.inventory.management.Dtos.FeedbackDTO;
import com.inventory.management.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<FeedbackDTO> createFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        return ResponseEntity.ok(feedbackService.saveFeedback(feedbackDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackDTO> updateFeedback(@PathVariable Long id, @RequestBody FeedbackDTO feedbackDTO) {
        return ResponseEntity.ok(feedbackService.updateFeedback(id, feedbackDTO));
    }

    @GetMapping
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks() {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> getFeedbackById(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getFeedbackById(id));
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<FeedbackDTO>> getFeedbacksByBusinessId(@PathVariable String businessId) {
        return ResponseEntity.ok(feedbackService.getFeedbacksByBusinessId(businessId));
    }
}

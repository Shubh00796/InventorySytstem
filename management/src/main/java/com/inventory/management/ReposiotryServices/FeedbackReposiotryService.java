package com.inventory.management.ReposiotryServices;

import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.Feedback;
import com.inventory.management.Repo.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackReposiotryService {
    private final FeedbackRepository repository;

    public List<Feedback> retriveAllFeedbacks() {
        return repository.findAll();
    }

    public Feedback retriveFeedbackById(Long feedbackId) {
        return getFeedback(feedbackId);


    }

    public List<Feedback> retriveFeedbackByBusinessId(String bId) {
        return repository.findByBusinessId(bId);
    }

    public Feedback saveFeedback(Feedback feedback) {
        return repository.save(feedback);
    }

    private Feedback getFeedback(Long feedbackId) {
        return repository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback with given id not found" + feedbackId));
    }
}

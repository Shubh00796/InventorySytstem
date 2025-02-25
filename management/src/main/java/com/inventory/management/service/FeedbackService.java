package com.inventory.management.service;

import com.inventory.management.Dtos.FeedbackDTO;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {

    FeedbackDTO saveFeedback(FeedbackDTO feedbackDTO);

    FeedbackDTO updateFeedback(Long feedbackId , FeedbackDTO feedbackDTO);

    List<FeedbackDTO> getAllFeedbacks();

    FeedbackDTO getFeedbackById(Long id);

    List<FeedbackDTO> getFeedbacksByBusinessId(String businessId);

}

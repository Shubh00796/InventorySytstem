package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.FeedbackDTO;
import com.inventory.management.Mapper.FeedbackMapper;
import com.inventory.management.Model.Feedback;
import com.inventory.management.ReposiotryServices.FeedbackReposiotryService;
import com.inventory.management.events.FlightPathOptimizationEvent;
import com.inventory.management.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {
    private final ApplicationEventPublisher eventPublisher;
    private final FeedbackReposiotryService repositoryService;
    private final FeedbackMapper mapper;
    private final ConcurrentHashMap<Long, ReentrantLock> feedbackLocks = new ConcurrentHashMap<>();

    @Override
    public FeedbackDTO saveFeedback(FeedbackDTO feedbackDTO) {
        Feedback entity = mapper.toEntity(feedbackDTO);
        entity.setCreatedAt(LocalDateTime.now());
        Feedback savedFeedback = repositoryService.saveFeedback(entity);

        publishFeedbackEvent(savedFeedback.getId());

        return mapper.toDto(savedFeedback);
    }

    @Override
    public FeedbackDTO updateFeedback(Long feedbackId, FeedbackDTO feedbackDTO) {
        validateId(feedbackId);
        ReentrantLock lock = feedbackLocks.computeIfAbsent(feedbackId, id -> new ReentrantLock());

        lock.lock();
        try {
            Feedback feedback = getFeedbackEntityById(feedbackId);
            mapper.updateEntityFromDto(feedbackDTO, feedback);
            return mapper.toDto(repositoryService.saveFeedback(feedback));
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<FeedbackDTO> getAllFeedbacks() {
        return repositoryService.retriveAllFeedbacks()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public FeedbackDTO getFeedbackById(Long feedbackId) {
        return mapper.toDto(getFeedbackEntityById(feedbackId));
    }

    @Override
    public List<FeedbackDTO> getFeedbacksByBusinessId(String businessId) {
        return repositoryService.retriveFeedbackByBusinessId(businessId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    private void publishFeedbackEvent(Long feedbackId) {
        eventPublisher.publishEvent(new FlightPathOptimizationEvent(this, feedbackId));
    }

    private Feedback getFeedbackEntityById(Long id) {
        return repositoryService.retriveFeedbackById(id);
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Feedback ID cannot be null");
        }
    }
}

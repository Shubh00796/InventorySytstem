package com.inventory.management.events;

import com.inventory.management.Enums.Sentiment;
import com.inventory.management.Model.Feedback;
import com.inventory.management.ReposiotryServices.FeedbackReposiotryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FeedbackProcessingEventListener {

    private final FeedbackReposiotryService feedbackRepository;

    @Async
    @EventListener
    @Transactional
    public void handleFeedbackReceivedEvent(FeedbackReceivedEvent event) {
        Feedback feedback = event.getFeedback();
        log.info("Processing feedback (ID: {}) for sentiment analysis", feedback.getId());

        // Use keyword-based analysis instead of Stanford NLP
        String sentiment = analyzeSentiment(feedback.getReview(), feedback.getRating());

        if (sentiment != null) {
            feedback.setSentiment(Sentiment.valueOf(sentiment));
            feedbackRepository.saveFeedback(feedback);
            log.info("Feedback (ID: {}) updated with sentiment: {}", feedback.getId(), sentiment);
        } else {
            log.warn("Sentiment analysis failed for feedback (ID: {}).");
        }
    }

    /**
     * A very basic sentiment analysis implementation.
     * This method checks the review text for positive and negative keywords
     * and considers the numerical rating to determine the overall sentiment.
     */
    private String analyzeSentiment(String review, int rating) {
        // Define basic sets of keywords
        List<String> negativeWords = Arrays.asList("bad", "poor", "terrible", "awful", "worst");
        List<String> positiveWords = Arrays.asList("good", "great", "excellent", "amazing", "best");

        long negativeCount = negativeWords.stream()
                .filter(word -> review.toLowerCase().contains(word))
                .count();
        long positiveCount = positiveWords.stream()
                .filter(word -> review.toLowerCase().contains(word))
                .count();

        // Determine sentiment based on rating and keyword counts:
        if (rating <= 2 || negativeCount > positiveCount) {
            return "NEGATIVE";
        } else if (rating >= 4 || positiveCount > negativeCount) {
            return "POSITIVE";
        } else {
            return "NEUTRAL";
        }
    }
}

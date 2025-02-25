package com.inventory.management.events;

import com.inventory.management.Model.Feedback;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class FeedbackReceivedEvent extends ApplicationEvent {
    private final Feedback feedback;

    public FeedbackReceivedEvent(Object source, Feedback feedback) {
        super(source);
        this.feedback = feedback;
    }
}

package com.inventory.management.Factories;

import com.inventory.management.Model.Video;
import org.springframework.stereotype.Component;

@Component
public class VideoFactory implements ContentFactory {
    @Override
    public Video createContent(String id, String title, String description) {
        return new Video(id, title, "Video", description, "Unknown Duration");
    }
}

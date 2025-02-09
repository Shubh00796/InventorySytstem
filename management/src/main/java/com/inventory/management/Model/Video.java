package com.inventory.management.Model;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video extends Content {
    private String duration;

    public Video(String id, String title, String video, String description, String unknownDuration) {
    }
}
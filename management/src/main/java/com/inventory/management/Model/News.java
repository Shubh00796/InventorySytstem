package com.inventory.management.Model;


import lombok.*;

@Data
@NoArgsConstructor
public class News extends Content {
    private String source;

    public News(String id, String title, String type, String description, String source) {
        super(id, title, type, description);
        this.source = source;
    }
}
package com.inventory.management.Model;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class News extends Content {
    private String source;

    public News(String id, String title, String news, String description, String unknownSource) {
    }
}
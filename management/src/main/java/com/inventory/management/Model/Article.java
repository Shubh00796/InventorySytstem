package com.inventory.management.Model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article extends Content {
    private String author;

    public Article(String id, String title, String article, String description, String unknownAuthor) {
    }
}
package com.inventory.management.Factories;

import com.inventory.management.Model.Article;
import com.inventory.management.Model.Content;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class DefaultContentFactory implements ContentFactory {
    @Override
    public Content createContent(String id, String title, String description) {
        return new Article(id, title, "Article", description, "Unknown Author");
    }
}

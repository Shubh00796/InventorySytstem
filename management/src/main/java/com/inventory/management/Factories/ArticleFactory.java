package com.inventory.management.Factories;

import com.inventory.management.Model.Article;
import com.inventory.management.Model.Content;
import org.springframework.stereotype.Component;

@Component("articleFactory")
public class ArticleFactory implements ContentFactory {
    @Override
    public Content createContent(String id, String title, String description) {
        return new Article(id, title, "Article", description, "Default Author");
    }

}
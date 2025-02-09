package com.inventory.management.Factories;

import com.inventory.management.Model.News;
import org.springframework.stereotype.Component;

@Component("newsFactory")
public class NewsFactory implements ContentFactory {
    @Override
    public News createContent(String id, String title, String description) {
        return new News(id, title, "News", description, "Unknown Source");
    }
}

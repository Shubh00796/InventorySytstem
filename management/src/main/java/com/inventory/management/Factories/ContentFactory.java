package com.inventory.management.Factories;

import com.inventory.management.Model.Content;

public interface ContentFactory {
    Content createContent(String id, String title, String description);
}

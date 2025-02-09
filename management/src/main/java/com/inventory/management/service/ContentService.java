package com.inventory.management.service;

import com.inventory.management.Dtos.ContentDTO;
import com.inventory.management.Factories.ContentFactory;

import java.util.List;

public interface ContentService {
    ContentDTO createContent(ContentFactory factory, String id, String title, String description);
    List<ContentDTO> getAllContent();
    ContentDTO getContentById(String id);
    void deleteContent(String id);
}
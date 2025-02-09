package com.inventory.management.Repo;

import com.inventory.management.Model.Content;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ContentRepository {
    private final Map<String, Content> contentStorage = new HashMap<>();

    public void save(Content content) {
        contentStorage.put(content.getId(), content);
    }

    public Optional<Content> findById(String id) {
        return Optional.ofNullable(contentStorage.get(id));
    }

    public List<Content> findAll() {
        return new ArrayList<>(contentStorage.values());
    }

    public void deleteById(String id) {  // ✅ Fixed method name
        contentStorage.remove(id);
    }
}

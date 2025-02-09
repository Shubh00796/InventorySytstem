package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.ContentDTO;
import com.inventory.management.Factories.ContentFactory;
import com.inventory.management.Mapper.ContentMapper;
import com.inventory.management.Model.Content;
import com.inventory.management.Repo.ContentRepository;
import com.inventory.management.service.ContentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;

    @Override
    @CircuitBreaker(name = "contentService", fallbackMethod = "fallbackMethod")
    @RateLimiter(name = "contentService")
    public ContentDTO createContent(ContentFactory factory, String id, String title, String description) {
        System.out.println("Received ID: " + id);
        System.out.println("Received Title: " + title);
        System.out.println("Received Description: " + description);
        Content content = factory.createContent(id, title, description);
        System.out.println("Created Content: " + content);

        contentRepository.save(content);

        return contentMapper.toDTO(content); // ✅ Ensures Mapping is Applied

    }


    @Override
    public List<ContentDTO> getAllContent() {
        return contentRepository.findAll().stream()
                .map(contentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ContentDTO getContentById(String id) {
        return contentRepository.findById(id)
                .map(contentMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Content not found with ID: " + id));
    }

    @Override
    public void deleteContent(String id) {
        contentRepository.delete(id);
    }


    public ContentDTO fallbackMethod(Exception ex) {
        return new ContentDTO("N/A", "Service Down", "N/A", "Fallback Content");
    }
}

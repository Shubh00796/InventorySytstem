package com.inventory.management.ReposiotryServices;

import com.inventory.management.Dtos.QuoteDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.Quote;
import com.inventory.management.Repo.QuoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuoteReposiotryService {
    private final QuoteRepository quoteRepository;


    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }

    public Optional<Quote> getQuoteById(Long id) {
        return quoteRepository.findById(id);
    }

    public Quote saveQuote(Quote quote) {
        return quoteRepository.save(quote);
    }

    public Quote updateQuote(Long id, QuoteDTO quoteDTO) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" ID " + id + " not found"));

        Optional.ofNullable(quoteDTO.getAuthor()).ifPresent(quote::setAuthor);
        Optional.ofNullable(quoteDTO.getContent()).ifPresent(quote::setContent);

        return quoteRepository.save(quote);
    }

    public void deleteQuote(Long id) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor reading not found with id: " + id));

        quoteRepository.delete(quote);

    }


}

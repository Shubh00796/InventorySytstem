package com.inventory.management.service;

import com.inventory.management.Dtos.QuoteDTO;

import java.util.List;

public interface QuoteService {
    List<QuoteDTO> getAllQuotes();
    QuoteDTO getQuoteById(Long id);
    QuoteDTO createQuote(QuoteDTO quoteDTO);
    QuoteDTO updateQuote(Long id, QuoteDTO quoteDTO);
    void deleteQuote(Long id);
}
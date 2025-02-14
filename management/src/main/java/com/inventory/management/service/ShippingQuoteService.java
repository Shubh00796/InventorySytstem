package com.inventory.management.service;

import com.inventory.management.Dtos.ShippingQuoteDTO;
import com.inventory.management.Model.ShippingQuote;

import java.util.List;
import java.util.Optional;

public interface ShippingQuoteService {
    List<ShippingQuoteDTO> getAllQuotes();
    Optional<ShippingQuoteDTO> getQuoteById(Long id);
    ShippingQuoteDTO createQuote(ShippingQuoteDTO quote);
    ShippingQuoteDTO updateQuote(Long id, ShippingQuoteDTO quoteDTO);
    void deleteQuote(Long id);
}
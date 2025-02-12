package com.inventory.management.Mapper;

import com.inventory.management.Dtos.QuoteDTO;
import com.inventory.management.Model.Quote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuoteMapper {

    QuoteDTO quoteToQuoteDTO(Quote quote);
    Quote quoteDTOToQuote(QuoteDTO quoteDTO);
}
package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.QuoteDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Mapper.QuoteMapper;
import com.inventory.management.Model.Quote;
import com.inventory.management.ReposiotryServices.QuoteReposiotryService;
import com.inventory.management.ValidatorLogics.QuoteValidator;
import com.inventory.management.service.QuoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {

    private final QuoteReposiotryService quoteReposiotryService;
    private final QuoteMapper quoteMapper;

    @Override
    public List<QuoteDTO> getAllQuotes() {
        return quoteReposiotryService.getAllQuotes()
                .stream()
                .map(quoteMapper::quoteToQuoteDTO)
                .collect(Collectors.toList());
    }

    @Override
    public QuoteDTO getQuoteById(Long id) {
        return quoteReposiotryService.getQuoteById(id)
                .map(quoteMapper::quoteToQuoteDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Quote Id not found for given id :" + id));
    }

    @Override
    public QuoteDTO createQuote(QuoteDTO quoteDTO) {
        QuoteValidator.validate(quoteDTO);
        Quote savedInQuote = quoteMapper.quoteDTOToQuote(quoteDTO);
        Quote savedInRepo = quoteReposiotryService.saveQuote(savedInQuote);
        return quoteMapper.quoteToQuoteDTO(savedInRepo);
    }

    @Override
    public QuoteDTO updateQuote(Long id, QuoteDTO quoteDTO) {
        QuoteValidator.validate(quoteDTO);

        Quote updatedQuotes = quoteReposiotryService.updateQuote(id, quoteDTO);
        return quoteMapper.quoteToQuoteDTO(updatedQuotes);

    }

    @Override
    public void deleteQuote(Long id) {
        quoteReposiotryService.deleteQuote(id);

    }
}




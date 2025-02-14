package com.inventory.management.ServiceImpl;

import com.inventory.management.DesignPatternInterfaces.ShippingCarrier;
import com.inventory.management.Dtos.ShippingQuoteDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.FactoryInterfaceImpl.ShippingCalculatorFactory;
import com.inventory.management.FactoryInterfaceImpl.ShippingCarrierFactory;
import com.inventory.management.HelperClasses.ShippingCalculator;
import com.inventory.management.Mapper.ShippingQuoteMapper;
import com.inventory.management.Model.ShippingQuote;
import com.inventory.management.ReposiotryServices.ShippingReposiotryService;
import com.inventory.management.service.ShippingQuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ShippingQuoteServiceImpl implements ShippingQuoteService {

    private final ShippingQuoteMapper mapper;
    private final ShippingCalculatorFactory calculatorFactory;
    private final ShippingCarrierFactory carrierFactory;
    private final ShippingReposiotryService repository;

    @Override
    public List<ShippingQuoteDTO> getAllQuotes() {
        return repository.getAllShipments()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ShippingQuoteDTO> getQuoteById(Long id) {
        return repository.getShipmentsById(id)
                .map(mapper::toDTO);
    }

    @Override
    public ShippingQuoteDTO createQuote(ShippingQuoteDTO quoteDTO) {
        double cost = calculateShippingCost(quoteDTO);
        ShippingQuote quote = mapper.toEntity(quoteDTO);
        quote.setCost(cost);

        return mapper.toDTO(repository.saveShipments(quote));
    }

    @Override
    public ShippingQuoteDTO updateQuote(Long id, ShippingQuoteDTO quoteDTO) {
        ShippingQuote existingQuote = repository.getShipmentsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipping quote not found with id: " + id));



        return mapper.toDTO(repository.updateShipments(id, quoteDTO));
    }

    @Override
    public void deleteQuote(Long id) {
        ShippingQuote existingQuote = repository.getShipmentsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipping quote not found with id: " + id));

        repository.deleteShipment(existingQuote.getId());
    }

    private double calculateShippingCost(ShippingQuoteDTO quoteDTO) {
        ShippingCarrier carrier = carrierFactory.getShippingCarrier(quoteDTO.getCarrier());
        ShippingCalculator calculator = calculatorFactory.getShippingCalculator(quoteDTO.getShippingMethod(), carrier);
        return calculator.calculateCost(quoteDTO.getWeight(), quoteDTO.getDistance());
    }
}

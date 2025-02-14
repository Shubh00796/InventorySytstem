package com.inventory.management.ReposiotryServices;

import com.inventory.management.Dtos.ShippingQuoteDTO;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import com.inventory.management.Model.ShippingQuote;
import com.inventory.management.Repo.ShippingQuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShippingReposiotryService {
    private final ShippingQuoteRepository repository;


    public List<ShippingQuote> getAllShipments() {
        return repository.findAll();
    }

    public Optional<ShippingQuote> getShipmentsById(Long id) {
        return repository.findById(id);
    }

    public ShippingQuote saveShipments(ShippingQuote shippingQuote) {
        return repository.save(shippingQuote);
    }

    public ShippingQuote updateShipments(Long id, ShippingQuoteDTO shippingQuoteDTO) {
        ShippingQuote shippingQuote = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment With Given Id Not Found" + id));

        Optional.ofNullable(shippingQuoteDTO.getCost()).ifPresent(shippingQuote::setCost);
        Optional.ofNullable(shippingQuoteDTO.getShippingMethod()).ifPresent(shippingQuote::setShippingMethod);
        Optional.ofNullable(shippingQuoteDTO.getDistance()).ifPresent(shippingQuote::setDistance);
        Optional.ofNullable(shippingQuoteDTO.getCarrier()).ifPresent(shippingQuote::setCarrier);
        Optional.ofNullable(shippingQuoteDTO.getWeight()).ifPresent(shippingQuote::setWeight);




        return repository.save(shippingQuote);
    }

    public void deleteShipment(Long id) {
        ShippingQuote shippingQuote = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment With Given Id Not Found" + id));

        repository.delete(shippingQuote);
    }
}

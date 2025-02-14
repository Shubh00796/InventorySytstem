package com.inventory.management.Domain.RepoService;

import com.inventory.management.Domain.Entity.Ddelivery;
import com.inventory.management.Domain.Repo.DdeliveryRepository;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DdeliveryRepoService {

private final DdeliveryRepository repository;

    public List<Ddelivery> gettAll() {
        return repository.findAll();
    }

    public Optional<Ddelivery> getDeliveryById(UUID id) {
        return repository.findById(id);
    }

    public Ddelivery saveDelivery(Ddelivery ddelivery) {
        return repository.save(ddelivery);
    }

    public Ddelivery updateDelivery(UUID id, Ddelivery ddelivery) {
        Ddelivery updateddelivery = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID NOT FOUND " + id));

        Optional.ofNullable(ddelivery.getSender()).ifPresent(updateddelivery::setSender);
        Optional.ofNullable(ddelivery.getRecipient()).ifPresent(updateddelivery::setRecipient);
        Optional.ofNullable(ddelivery.getPackageWeight()).ifPresent(updateddelivery::setPackageWeight);
        Optional.ofNullable(ddelivery.getPackageType()).ifPresent(updateddelivery::setPackageType);

        return repository.save(ddelivery);

    }

    public void deleteDelivery(UUID id) {
        Ddelivery ddelivery = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID NOT FOUND " + id));

        repository.delete(ddelivery);
    }
}

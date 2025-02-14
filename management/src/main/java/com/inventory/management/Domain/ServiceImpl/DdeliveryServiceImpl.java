package com.inventory.management.Domain.ServiceImpl;

import com.inventory.management.Domain.DeleveryCostHelpers.ShippingCostService;
import com.inventory.management.Domain.DomainService.DdeliveryService;
import com.inventory.management.Domain.Entity.Ddelivery;
import com.inventory.management.Domain.RepoService.DdeliveryRepoService;
import com.inventory.management.Exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class DdeliveryServiceImpl implements DdeliveryService {

    private final DdeliveryRepoService deliveryRepository;
    private final ShippingCostService shippingCostService;

    @Override
    public Ddelivery createDelivery(Ddelivery ddelivery) {

        double shippingCost = shippingCostService.calculateShippingCost(ddelivery);
        ddelivery.setShippingCost(shippingCost);


        return deliveryRepository.saveDelivery(ddelivery);
    }

    @Override
    public Ddelivery getDelivery(UUID id) {
        return deliveryRepository.getDeliveryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found with id: " + id));
    }

    @Override
    public Ddelivery updateDelivery(UUID id, Ddelivery ddelivery) {
        double shippingCost = shippingCostService.calculateShippingCost(ddelivery);
        ddelivery.setShippingCost(shippingCost);

        return deliveryRepository.updateDelivery(id, ddelivery);
    }

    @Override
    public void deleteDelivery(UUID id) {
        deliveryRepository.deleteDelivery(id);

    }

    @Override
    public List<Ddelivery> listDeliveries() {
        return deliveryRepository.gettAll();
    }


}

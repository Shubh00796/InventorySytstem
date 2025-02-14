package com.inventory.management.Domain.DomainService;

import com.inventory.management.Domain.Entity.Ddelivery;

import java.util.List;
import java.util.UUID;

public interface DdeliveryService {
    Ddelivery createDelivery(Ddelivery ddelivery);
    Ddelivery getDelivery(UUID id);
    Ddelivery updateDelivery(UUID id, Ddelivery ddelivery);
    void deleteDelivery(UUID id);
    List<Ddelivery> listDeliveries();
}
package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.DeliveryDTO;
import com.inventory.management.Mapper.DeliveryMapper;
import com.inventory.management.Model.Delivery;
import com.inventory.management.Repo.DeliveryRepository;
import com.inventory.management.Utility.DeliveryValidator;
import com.inventory.management.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;
    private final DeliveryValidator deliveryValidator;

    @Override
    public List<DeliveryDTO> getAllDeliveries() {
        return deliveryRepository.findAll().stream()
                .map(deliveryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DeliveryDTO getDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Delivery ID not found"));
        return deliveryMapper.toDTO(delivery);
    }

    @Override
    @Transactional
    public DeliveryDTO createDelivery(DeliveryDTO deliveryDTO) {
        deliveryValidator.validateDeliveryDTO(deliveryDTO);
        Delivery delivery = deliveryRepository.save(deliveryMapper.toEntity(deliveryDTO));
        return deliveryMapper.toDTO(delivery);
    }

    @Override
    @Transactional
    public DeliveryDTO updateDelivery(Long id, DeliveryDTO deliveryDTO) {
        Delivery delivery = getDelivery(id, deliveryDTO);

        return deliveryMapper.toDTO(delivery);
    }

    private Delivery getDelivery(Long id, DeliveryDTO deliveryDTO) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Delivery ID not found"));

        deliveryValidator.validateTrackingNumber(id, deliveryDTO.getTrackingNumber());
        updateDeliveryFields(delivery, deliveryDTO);
        deliveryRepository.save(delivery);
        return delivery;
    }


    @Override
    public DeliveryDTO getByTrackingNumber(String trackingNumber) {
        Delivery delivery = deliveryRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new IllegalArgumentException("Tracking number not found"));
        return deliveryMapper.toDTO(delivery);
    }

    @Override
    @Transactional
    public void deleteDelivery(Long id) {
        if (!deliveryRepository.existsById(id)) {
            throw new IllegalArgumentException("Delivery ID not found");
        }
        deliveryRepository.deleteById(id);
    }

    private void updateDeliveryFields(Delivery delivery, DeliveryDTO deliveryDTO) {
        if (deliveryDTO.getDeliveryAddress() != null) {
            delivery.setDeliveryAddress(deliveryDTO.getDeliveryAddress());
        }
        if (deliveryDTO.getPickupAddress() != null) {
            delivery.setPickupAddress(deliveryDTO.getPickupAddress());
        }
        if (deliveryDTO.getEstimatedDeliveryTime() != null) {
            delivery.setEstimatedDeliveryTime(deliveryDTO.getEstimatedDeliveryTime());}
        if (deliveryDTO.getStatus() != null) {
            deliveryValidator.validateStatusTransition(delivery.getStatus(), deliveryDTO.getStatus());
            delivery.setStatus(deliveryDTO.getStatus());
        }
        if (deliveryDTO.getCourier() != null) {
            delivery.setCourier(deliveryDTO.getCourier());
        }
    }
}

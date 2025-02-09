package com.inventory.management.Mapper;

import com.inventory.management.Dtos.DeliveryDTO;
import com.inventory.management.Dtos.RestaurantDTO;
import com.inventory.management.Model.Delivery;
import com.inventory.management.Model.Restaurant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    Restaurant toEntity(RestaurantDTO dto);
    RestaurantDTO toDTO(Restaurant entity);
}
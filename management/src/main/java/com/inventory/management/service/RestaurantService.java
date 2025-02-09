package com.inventory.management.service;

import com.inventory.management.Dtos.RestaurantDTO;

import java.util.List;

public interface  RestaurantService {
    List<RestaurantDTO> getAllRestaurants();
    RestaurantDTO getRestaurantById(Long id);
    RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO);
    RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO);
    void deleteRestaurant(Long id);

}

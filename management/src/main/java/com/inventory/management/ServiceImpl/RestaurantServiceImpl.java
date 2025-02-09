package com.inventory.management.ServiceImpl;

import com.inventory.management.Dtos.RestaurantDTO;
import com.inventory.management.Mapper.RestaurantMapper;
import com.inventory.management.Model.Restaurant;
import com.inventory.management.Repo.RestaurantRepository;
import com.inventory.management.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantDTO getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with id: " + id));
        return restaurantMapper.toDTO(restaurant);
    }

    @Override
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByName(restaurantDTO.getName());
        if (restaurantOptional.isPresent()) {
            throw new IllegalArgumentException("The Restaurant name is already taken");
        }


        Restaurant savedToEntity = restaurantMapper.toEntity(restaurantDTO);
        Restaurant savedToRepo = restaurantRepository.save(savedToEntity);

        return restaurantMapper.toDTO(savedToRepo);
    }

    @Override
    public RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO) {
        Restaurant restaurant = updateNonNullFields(id, restaurantDTO);
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toDTO(updatedRestaurant);
    }

    @Override
    public void deleteRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with id: " + id));
        restaurantRepository.delete(restaurant);
    }

    private Restaurant updateNonNullFields(Long id, RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id not Found"));

        Optional.ofNullable(restaurantDTO.getCuisine()).ifPresent(restaurant::setCuisine);
        Optional.ofNullable(restaurantDTO.getAddress()).ifPresent(restaurant::setAddress);
        Optional.ofNullable(restaurantDTO.getName()).ifPresent(restaurant::setName);
        Optional.ofNullable(restaurantDTO.getRating()).ifPresent(restaurant::setRating);

        return restaurant;
    }
}

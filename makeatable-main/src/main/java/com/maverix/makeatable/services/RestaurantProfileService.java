package com.maverix.makeatable.services;

import com.maverix.makeatable.dto.Restaurent.RestaurantManagerDto;
import com.maverix.makeatable.models.Restaurant;
import com.maverix.makeatable.models.User;
import com.maverix.makeatable.repositories.RestaurantRepository;
import com.maverix.makeatable.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RestaurantProfileService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public RestaurantProfileService(UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public RestaurantManagerDto getManagerProfile(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByUser_Id(userId);
        Restaurant restaurant = restaurantOptional.orElseThrow(() -> new RuntimeException("No restaurant associated with this user"));

        RestaurantManagerDto managerDto = new RestaurantManagerDto();
        BeanUtils.copyProperties(restaurant, managerDto);

        return managerDto;
    }

    public void updateManagerProfile(Long userId, RestaurantManagerDto managerDto) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByUser_Id(userId);
        Restaurant restaurant = restaurantOptional.orElseThrow(() -> new RuntimeException("No restaurant associated with this user"));
        restaurant.setFullName(managerDto.getFullName());
        restaurant.setLocation(managerDto.getLocation());
        restaurant.setFoodType(managerDto.getFoodType());
        restaurant.setDescription(managerDto.getDescription());
        restaurant.setTypeRoom(managerDto.getTypeRoom());
        restaurantRepository.save(restaurant);
    }
}

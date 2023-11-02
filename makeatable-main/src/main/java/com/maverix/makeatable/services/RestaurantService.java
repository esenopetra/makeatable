package com.maverix.makeatable.services;
import com.maverix.makeatable.config.Security.JwtService;
import com.maverix.makeatable.dto.Restaurent.RestaurantFilterDto;
import com.maverix.makeatable.dto.Restaurent.RestaurantGetDto;
import com.maverix.makeatable.dto.Restaurent.RestaurantPostDto;
import com.maverix.makeatable.dto.Restaurent.RestaurantPutDto;
import com.maverix.makeatable.dto.Restaurent.RestaurantSearchGetDto;
import com.maverix.makeatable.enums.RestStatus;
import com.maverix.makeatable.exceptions.RestaurantNotFoundException;
import com.maverix.makeatable.models.Restaurant;
import com.maverix.makeatable.repositories.RestaurantRepository;
import com.maverix.makeatable.repositories.UserRepository;
import com.maverix.makeatable.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final HttpServletRequest request;
    private final JwtUtils jwtUtils;
    private final JwtService jwtService;

    public RestaurantService(RestaurantRepository restaurantRepository, UserRepository userRepository, HttpServletRequest request, JwtUtils jwtUtils, JwtService jwtService) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.request = request;
        this.jwtUtils = jwtUtils;
        this.jwtService = jwtService;
    }

    public Long getCurrentUserRestaurantId(String token) {
        Long userId = Long.valueOf(jwtService.extractId(token));
        Restaurant restaurant = restaurantRepository.findByUserId(userId);
        if (restaurant != null) {
            return restaurant.getId();
        }
        return null;

    }

    public List<RestaurantGetDto> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream()
                .map(this::convertToGetDto)
                .collect(Collectors.toList());
    }

    public RestaurantGetDto getRestaurantById(Long id) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        return optionalRestaurant.map(this::convertToGetDto).orElse(null);
    }

    public RestaurantGetDto createRestaurant(RestaurantPostDto restaurantPostDto) {
        Restaurant restaurant = convertToEntity(restaurantPostDto);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return convertToGetDto(savedRestaurant);
    }

    public RestaurantGetDto approveRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.getById(id);
        restaurant.setStatus(RestStatus.APPROVED);
        restaurantRepository.save(restaurant);

        return convertToGetDto(restaurant);

    }

    public RestaurantGetDto declineRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.getById(id);
        restaurant.setStatus(RestStatus.DECLINED);
        restaurantRepository.save(restaurant);

        return convertToGetDto(restaurant);

    }

    public void deleteRestaurant(Long id) {

        restaurantRepository.deleteById(id);
    }

    private RestaurantGetDto convertToGetDto(Restaurant restaurant) {
        RestaurantGetDto restaurantGetDto = new RestaurantGetDto();
        restaurantGetDto.setRating(restaurant.getAverageRating());
        restaurantGetDto.setUserId(restaurant.getUser().getId());
        restaurantGetDto.setUserName(restaurant.getUser().getFullName());
        BeanUtils.copyProperties(restaurant, restaurantGetDto);
        return restaurantGetDto;
    }

    private Restaurant convertToEntity(RestaurantPostDto restaurantPostDto) {
        Restaurant restaurant = new Restaurant();
        BeanUtils.copyProperties(restaurantPostDto, restaurant);
        restaurant.setStatus(RestStatus.PENDING);
        restaurant.setCreatedAt(LocalDateTime.now());
        restaurant.setUpdatedAt(LocalDateTime.now());
        return restaurant;
    }


    public RestaurantGetDto updateRestaurant(Long id, RestaurantPutDto restaurantPutDto) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);

        if (optionalRestaurant.isPresent()) {
            Restaurant existingRestaurant = optionalRestaurant.get();
            existingRestaurant.setFullName(restaurantPutDto.getFullName());
            existingRestaurant.setMobileNum(restaurantPutDto.getMobileNum());
            existingRestaurant.setLocation(restaurantPutDto.getLocation());
            existingRestaurant.setEmail(restaurantPutDto.getEmail());
            existingRestaurant.setImageUrl(restaurantPutDto.getImageUrl());
            existingRestaurant.setOpenTime(restaurantPutDto.getOpenTime());
            existingRestaurant.setCloseTime(restaurantPutDto.getCloseTime());
            existingRestaurant.setFoodType(restaurantPutDto.getFoodType());
            existingRestaurant.setSeatNum(restaurantPutDto.getSeatNum());
            existingRestaurant.setDescription(restaurantPutDto.getDescription());
            existingRestaurant.setTypeRoom(restaurantPutDto.getTypeRoom());

            Restaurant updatedRestaurant = restaurantRepository.save(existingRestaurant);


            return convertToGetDto(updatedRestaurant);
        } else {
            throw new RestaurantNotFoundException("Restaurant not found with ID: " + id);
        }
    }

    public List<RestaurantGetDto> getTop5RatedRestaurants() {
        List<Restaurant> top5Restaurants = restaurantRepository.findTop5ByOrderByAverageRatingDesc();
        return top5Restaurants.stream()
                .map(this::convertToRestaurantDto)
                .collect(Collectors.toList());
    }

    private RestaurantGetDto convertToRestaurantDto(Restaurant restaurant) {
        RestaurantGetDto restaurantDto = new RestaurantGetDto();
        restaurantDto.setRating(restaurant.getAverageRating());
        BeanUtils.copyProperties(restaurant, restaurantDto);

        return restaurantDto;
    }

    public Restaurant getfullRestaurantById(Long restaurantId) {
        return restaurantRepository.getById(restaurantId);
    }


    public boolean isManagerOfRestaurant(Long restaurantId, Authentication authentication) {
        String username = authentication.getName();
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant != null) {
            return restaurant.getUser().getEmail().equals(username);
        }
        return false;
    }

    public boolean isManagerOfRestaurantbyId(Long restaurantId, Long jwtUserId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant != null) {
            return restaurant.getUser().getId().equals(jwtUserId);
        }
        return false;
    }

    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Optional<Restaurant> getRestaurantFullById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId);
    }

    public List<RestaurantSearchGetDto> searchRestaurant(String query) {
        List<Restaurant> restaurants = restaurantRepository.searchRestaurant(query);
        return restaurants.stream()
                .map(this::convertToRestaurantSearchGetDto)
                .collect(Collectors.toList());
    }
    public List<RestaurantGetDto> getRestaurantsByStatus(RestStatus status) {
        List<Restaurant> restaurants = restaurantRepository.findByStatus(status);
        return restaurants.stream()
                .map(this::convertToGetDto)
                .collect(Collectors.toList());
    }

    private RestaurantSearchGetDto convertToRestaurantSearchGetDto(Restaurant restaurant) {
        RestaurantSearchGetDto restaurantSearchDto = new RestaurantSearchGetDto();
        BeanUtils.copyProperties(restaurant, restaurantSearchDto);
        return restaurantSearchDto;
    }
    public List<RestaurantFilterDto> filterRestaurants(RestaurantFilterDto filterDto) {
        List<Restaurant> filteredRestaurants = restaurantRepository.filterRestaurantsByLocationAndFoodType(
                filterDto.getLocation(),
                filterDto.getFoodType()
        );

        List<RestaurantFilterDto> filteredDtoList = new ArrayList<>();
        for (Restaurant restaurant : filteredRestaurants) {
            RestaurantFilterDto filteredDto = new RestaurantFilterDto();
            filteredDto.setFullname(restaurant.getFullName());
            filteredDto.setLocation(restaurant.getLocation());
            filteredDto.setFoodType(restaurant.getFoodType());
            filteredDtoList.add(filteredDto);
        }

        return filteredDtoList;
    }

}
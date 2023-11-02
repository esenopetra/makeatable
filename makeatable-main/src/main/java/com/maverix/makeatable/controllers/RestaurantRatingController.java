package com.maverix.makeatable.controllers;

import com.maverix.makeatable.exceptions.RestaurantNotFoundException;
import com.maverix.makeatable.models.Restaurant;
import com.maverix.makeatable.models.RestaurantRating;
import com.maverix.makeatable.services.RestaurantRatingService;
import com.maverix.makeatable.services.RestaurantService;
import org.hibernate.Hibernate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/restaurant-ratings")
public class RestaurantRatingController {


    private final RestaurantRatingService restaurantRatingService;


    private final RestaurantService restaurantService;

    public RestaurantRatingController(RestaurantRatingService restaurantRatingService, RestaurantService restaurantService) {
        this.restaurantRatingService = restaurantRatingService;
        this.restaurantService = restaurantService;
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public RestaurantRating submitRating(@RequestParam Long restaurantId, @RequestParam Double rating) {
        Restaurant restaurant = restaurantService.getfullRestaurantById(restaurantId);
        if (restaurant != null) {
            Hibernate.initialize(restaurant.getRatings());
            Hibernate.initialize(restaurant.getUser());

            RestaurantRating restaurantRating = new RestaurantRating();
            restaurantRating.setRestaurant(restaurant);
            restaurantRating.setRating(rating);
            return restaurantRatingService.submitRating(restaurantRating);
        }
        throw new RestaurantNotFoundException("Restaurant not found with ID: " + restaurantId);
    }

}

package com.maverix.makeatable.services;



import com.maverix.makeatable.models.RestaurantRating;
import com.maverix.makeatable.repositories.RestaurantRatingRepository;
import org.springframework.stereotype.Service;

@Service
public class RestaurantRatingService {


    private final RestaurantRatingRepository restaurantRatingRepository;

    public RestaurantRatingService(RestaurantRatingRepository restaurantRatingRepository) {
        this.restaurantRatingRepository = restaurantRatingRepository;
    }

    public RestaurantRating submitRating(RestaurantRating restaurantRating) {
        return restaurantRatingRepository.save(restaurantRating);
    }
}

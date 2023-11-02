package com.maverix.makeatable.services;

import com.maverix.makeatable.exceptions.FoodNotFoundException;
import com.maverix.makeatable.models.Food;
import com.maverix.makeatable.models.FoodRating;
import com.maverix.makeatable.repositories.FoodRatingRepository;
import com.maverix.makeatable.repositories.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FoodRatingService {

    private final FoodRatingRepository foodRatingRepository;
    private final FoodRepository foodRepository;

    public FoodRatingService(FoodRatingRepository foodRatingRepository, FoodRepository foodRepository) {
        this.foodRatingRepository = foodRatingRepository;
        this.foodRepository = foodRepository;
    }

    public FoodRating submitRating(Long foodId, Double newRating) {
        Optional<Food> foodOptional = foodRepository.findById(foodId);

        if (foodOptional.isEmpty()) {
            throw new FoodNotFoundException("Food not found with ID: " + foodId);
        }

        Food food = foodOptional.get();

        FoodRating foodRating = new FoodRating();
        foodRating.setFood(food);
        foodRating.setRating(newRating);
        foodRating.setRatingNum(1L);

        return foodRatingRepository.save(foodRating);
    }
}

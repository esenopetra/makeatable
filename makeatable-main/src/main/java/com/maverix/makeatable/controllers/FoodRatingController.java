package com.maverix.makeatable.controllers;

import com.maverix.makeatable.models.FoodRating;
import com.maverix.makeatable.services.FoodRatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/food-ratings")
public class FoodRatingController {


    private final FoodRatingService foodRatingService;

    public FoodRatingController(FoodRatingService foodRatingService) {
        this.foodRatingService = foodRatingService;
    }

    @PostMapping("/submit-rating")
    public ResponseEntity<FoodRating> submitRating(
            @RequestParam Long food_id,
            @RequestParam Double rating) {

        FoodRating foodRating = foodRatingService.submitRating(food_id, rating);
        return ResponseEntity.status(HttpStatus.OK).body(foodRating);
    }
}

package com.maverix.makeatable.controllers;

import com.maverix.makeatable.dto.Food.FoodGetDto;
import com.maverix.makeatable.dto.Restaurent.RestaurantFilterDto;
import com.maverix.makeatable.dto.Restaurent.RestaurantGetDto;
import com.maverix.makeatable.dto.Restaurent.RestaurantSearchGetDto;
import com.maverix.makeatable.enums.FoodCategory;
import com.maverix.makeatable.services.FoodService;
import com.maverix.makeatable.services.RestaurantService;
import com.maverix.makeatable.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/public")
public class PublicController {
    private final RestaurantService restaurantService;
    private final FoodService foodService;

    public PublicController(RestaurantService restaurantService, FoodService foodService) {
        this.restaurantService = restaurantService;
        this.foodService = foodService;
    }

    @GetMapping("/top-rated")
    public ResponseEntity<Response<List<RestaurantGetDto>>> getTop5RatedRestaurants() {
        List<RestaurantGetDto> topRatedRestaurants = restaurantService.getTop5RatedRestaurants();

        Response<List<RestaurantGetDto>> response = Response.<List<RestaurantGetDto>>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(200)
                .status(org.springframework.http.HttpStatus.OK)
                .message("Top 5 rated restaurants fetched successfully")
                .data(topRatedRestaurants)
                .build();

        return ResponseEntity.ok(response);
    }
    @GetMapping("/search")
    public ResponseEntity<List<RestaurantSearchGetDto>> searchRestaurant(@RequestParam("query") String query) {
        List<RestaurantSearchGetDto> searchResults = restaurantService.searchRestaurant(query);
        return ResponseEntity.ok(searchResults);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<RestaurantFilterDto>> filterRestaurants(@RequestParam("location") String location, @RequestParam("foodType") FoodCategory foodType) {

        RestaurantFilterDto filterDto = new RestaurantFilterDto();
        filterDto.setLocation(location);
        filterDto.setFoodType(foodType);

        List<RestaurantFilterDto> filteredRestaurants = restaurantService.filterRestaurants(filterDto);

        return ResponseEntity.ok(filteredRestaurants);
    }
    @GetMapping
    public ResponseEntity<Response<List<FoodGetDto>>> getAllFoods() {
        List<FoodGetDto> foods = foodService.getAllFoods();
        Response<List<FoodGetDto>> response = Response.<List<FoodGetDto>>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Foods retrieved successfully")
                .data(foods)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<FoodGetDto>> getFoodById(@PathVariable Long id) {
        FoodGetDto food = foodService.getFoodById(id);
        Response<FoodGetDto> response = Response.<FoodGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Food retrieved successfully")
                .data(food)
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/top")
    public ResponseEntity<Response<List<FoodGetDto>>> getTopFoods() {
        List<FoodGetDto> topFoods = foodService.getTop5RatedFoods();
        Response<List<FoodGetDto>> response = Response.<List<FoodGetDto>>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Top 5 foods retrieved successfully")
                .data(topFoods)
                .build();
        return ResponseEntity.ok(response);
    }
}

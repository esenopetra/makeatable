package com.maverix.makeatable.services;
import com.maverix.makeatable.dto.Food.FoodGetDto;
import com.maverix.makeatable.dto.Food.FoodPostDto;
import com.maverix.makeatable.dto.Food.FoodPutDto;
import com.maverix.makeatable.models.Food;
import com.maverix.makeatable.models.Restaurant;
import com.maverix.makeatable.repositories.FoodRepository;
import com.maverix.makeatable.repositories.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;


    public FoodService(FoodRepository foodRepository, RestaurantRepository restaurantRepository) {
        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;

    }

    public List<FoodGetDto> getAllFoods() {
        List<Food> foods = foodRepository.findAll();
        return foods.stream()
                .map(this::convertToFoodDto)
                .collect(Collectors.toList());
    }

    public FoodGetDto getFoodById(Long id) {
        Optional<Food> optionalFood = foodRepository.findById(id);
        return optionalFood.map(this::convertToFoodDto).orElse(null);
    }

    public FoodGetDto createFood(FoodPostDto foodPostDto) {
        Food food = convertToFoodEntity(foodPostDto);
        Food savedFood = foodRepository.save(food);
        return convertToFoodDto(savedFood);
    }

    public FoodGetDto updateFood(Long id, FoodPutDto foodPutDto) {
        Optional<Food> optionalFood = foodRepository.findById(id);
        if (optionalFood.isPresent()) {
            Food existingFood = optionalFood.get();
            BeanUtils.copyProperties(foodPutDto, existingFood);
            existingFood.setUpdatedAt(LocalDateTime.now());
            Food updatedFood = foodRepository.save(existingFood);
            return convertToFoodDto(updatedFood);
        }
        return null;
    }

    public void deleteFood(Long id) {
        foodRepository.deleteById(id);
    }

    private FoodGetDto convertToFoodDto(Food food) {
        FoodGetDto foodDto = new FoodGetDto();
        foodDto.setRating(food.getAverageRating());
        BeanUtils.copyProperties(food, foodDto);

        return foodDto;
    }

    private Food convertToFoodEntity(FoodPostDto foodPostDto) {
        Food food = new Food();
        BeanUtils.copyProperties(foodPostDto, food);
        food.setCreatedAt(LocalDateTime.now());
        food.setUpdatedAt(LocalDateTime.now());
        return food;
    }
    public List<FoodGetDto> getTop5RatedFoods() {
        List<Food> top5Foods = foodRepository.findTop5ByOrderByAverageRatingDesc();
        return top5Foods.stream()
                .map(this::convertToFoodDto)
                .collect(Collectors.toList());
    }

    public FoodGetDto addFood(Long restaurantId, FoodPostDto foodPostDto) {
        if (foodPostDto == null || !isValidFoodPostDto(foodPostDto)) {
            throw new IllegalArgumentException("Invalid food creation request. Please provide valid food data.");
        }
        if (foodPostDto.getName() == null || foodPostDto.getName().isEmpty()
                || foodPostDto.getPrice() == null || foodPostDto.getCategory() == null) {
            throw new IllegalArgumentException("Food name, price, and category are required.");
        }

        Food food = convertToFoodEntity(foodPostDto);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        food.setRestaurant(restaurant);

        Food savedFood = foodRepository.save(food);
        return convertToFoodDto(savedFood);
    }
    private boolean isValidFoodPostDto(FoodPostDto foodPostDto) {
        return foodPostDto.getName() != null && foodPostDto.getCategory() != null;
    }

    public Optional<Food> getFullFoodById(Long foodId) {
        return foodRepository.findById(foodId);
    }

    public void saveFood(Food food) {
        foodRepository.save(food);
    }
}

package com.maverix.makeatable.controllers;
import com.maverix.makeatable.dto.Food.FoodGetDto;
import com.maverix.makeatable.dto.Food.FoodPostDto;
import com.maverix.makeatable.dto.Food.FoodPutDto;
import com.maverix.makeatable.repositories.RestaurantRepository;
import com.maverix.makeatable.services.FoodService;
import com.maverix.makeatable.services.RestaurantService;
import com.maverix.makeatable.util.JwtUtils;
import com.maverix.makeatable.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j

@RestController
@RequestMapping("api/foods")
public class FoodController {

    private final FoodService foodService;
    private final RestaurantService restaurantService;
    private final HttpServletRequest request;
    private final JwtUtils jwtUtils;
    private final RestaurantRepository restaurantRepository;

    public FoodController(FoodService foodService, RestaurantService restaurantService, HttpServletRequest request, JwtUtils jwtUtils, RestaurantRepository restaurantRepository) {
        this.foodService = foodService;
        this.restaurantService = restaurantService;
        this.request = request;
        this.jwtUtils = jwtUtils;
        this.restaurantRepository = restaurantRepository;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Response<FoodGetDto>> updateFood(@PathVariable Long id,
                                                           @RequestBody FoodPutDto foodPutDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isManager = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("MANAGER"));

        if (!isManager) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Response.<FoodGetDto>builder()
                            .timeStamp(LocalDateTime.now())
                            .statusCode(HttpStatus.FORBIDDEN.value())
                            .status(HttpStatus.FORBIDDEN)
                            .message("Access denied: User is not a manager")
                            .data(null)
                            .build());
        }

        if (!restaurantService.isManagerOfRestaurant(foodPutDto.getRestaurantId(), authentication)) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Response.<FoodGetDto>builder()
                            .timeStamp(LocalDateTime.now())
                            .statusCode(HttpStatus.FORBIDDEN.value())
                            .status(HttpStatus.FORBIDDEN)
                            .message("Access denied: User is not the manager of the specified restaurant")
                            .data(null)
                            .build());
        }

        FoodGetDto updatedFood = foodService.updateFood(id, foodPutDto);

        return ResponseEntity.ok(
                Response.<FoodGetDto>builder()
                        .timeStamp(LocalDateTime.now())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("Food updated successfully")
                        .data(updatedFood)
                        .build()
        );
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);

        Response<Void> response = Response.<Void>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.NO_CONTENT.value())
                .status(HttpStatus.NO_CONTENT)
                .message("Food deleted successfully")
                .build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }


    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping
    public ResponseEntity<Response<FoodGetDto>> addFood(@RequestBody FoodPostDto foodPostDto,
                                                        @RequestHeader("Authorization") String token) {
        Long restaurantId = restaurantService.getCurrentUserRestaurantId(token);

        FoodGetDto createdFood = foodService.addFood(restaurantId, foodPostDto);

        Response<FoodGetDto> response = Response.<FoodGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(201)
                .status(org.springframework.http.HttpStatus.CREATED)
                .message("Food created successfully")
                .data(createdFood)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

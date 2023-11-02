package com.maverix.makeatable.controllers;
import com.maverix.makeatable.config.Security.JwtService;
import com.maverix.makeatable.dto.Restaurent.*;
import com.maverix.makeatable.enums.FoodCategory;
import com.maverix.makeatable.services.RestaurantService;
import com.maverix.makeatable.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("restaurants")
public class RestaurantController {
    @Autowired
    private final RestaurantService restaurantService;

    @Autowired
    private final JwtService jwtService;

    public RestaurantController(RestaurantService restaurantService, JwtService jwtService) {
        this.restaurantService = restaurantService;
        this.jwtService = jwtService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<RestaurantGetDto>> getRestaurantById(@PathVariable Long id) {
        RestaurantGetDto restaurant = restaurantService.getRestaurantById(id);
        Response<RestaurantGetDto> response = Response.<RestaurantGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Restaurant retrieved successfully")
                .data(restaurant)
                .build();
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping
    public ResponseEntity<Response<RestaurantGetDto>> createRestaurant(@RequestBody RestaurantPostDto restaurantPostDto) {

        RestaurantGetDto createdRestaurant = restaurantService.createRestaurant(restaurantPostDto);

        Response<RestaurantGetDto> response = Response.<RestaurantGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED)
                .message("Restaurant created successfully")
                .data(createdRestaurant)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Response<RestaurantGetDto>> updateRestaurant(@PathVariable Long id,
                                                                       @RequestBody RestaurantPutDto restaurantPutDto,
                                                                       @RequestHeader("Authorization") String token) {


        Long userId = Long.valueOf(jwtService.extractId(token));
        if (!restaurantService.isManagerOfRestaurantbyId(id, userId)) {
            Response<RestaurantGetDto> forbiddenResponse = Response.<RestaurantGetDto>builder()
                    .timeStamp(LocalDateTime.now())
                    .statusCode(HttpStatus.FORBIDDEN.value())
                    .status(HttpStatus.FORBIDDEN)
                    .message("You do not have permission to update this restaurant")
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(forbiddenResponse);
        }
        RestaurantGetDto updatedRestaurant = restaurantService.updateRestaurant(id, restaurantPutDto);
        Response<RestaurantGetDto> response = Response.<RestaurantGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Restaurant updated successfully")
                .data(updatedRestaurant)
                .build();

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);

        Response<Void> response = Response.<Void>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.NO_CONTENT.value())
                .status(HttpStatus.NO_CONTENT)
                .message("Restaurant deleted successfully")
                .build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }


}

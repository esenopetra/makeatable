package com.maverix.makeatable.controllers;
import com.maverix.makeatable.dto.Restaurent.RestaurantManagerDto;
import com.maverix.makeatable.services.RestaurantProfileService;
import com.maverix.makeatable.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/restaurant-profile")
public class RestaurantProfileController {

    private final RestaurantProfileService restaurantProfileService;

    @Autowired
    public RestaurantProfileController(RestaurantProfileService restaurantProfileService) {
        this.restaurantProfileService = restaurantProfileService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Response<RestaurantManagerDto>> getManagerProfile(@PathVariable Long userId) {
        RestaurantManagerDto managerDto = restaurantProfileService.getManagerProfile(userId);

        Response<RestaurantManagerDto> response = Response.<RestaurantManagerDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(managerDto)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Response<String>> updateManagerProfile(@PathVariable Long userId, @RequestBody RestaurantManagerDto managerDto) {
        restaurantProfileService.updateManagerProfile(userId, managerDto);

        Response<String> response = Response.<String>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Restaurant profile updated successfully.")
                .build();

        return ResponseEntity.ok(response);
    }
}

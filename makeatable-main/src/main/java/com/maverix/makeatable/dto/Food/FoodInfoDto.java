package com.maverix.makeatable.dto.Food;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class FoodInfoDto {
    @NotBlank(message = "Food name cannot be blank")
    @Size(max = 100, message = "Food name length must be less than or equal to 100 characters")
    private String foodName;

    @Size(max = 255, message = "Image URL length must be less than or equal to 255 characters")
    private String imageUrl;

    @NotBlank(message = "Restaurant name cannot be blank")
    @Size(max = 100, message = "Restaurant name length must be less than or equal to 100 characters")
    private String restaurantName;
}
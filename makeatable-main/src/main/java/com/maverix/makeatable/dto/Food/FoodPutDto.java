package com.maverix.makeatable.dto.Food;

import com.maverix.makeatable.enums.FoodCategory;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class FoodPutDto {
    @Size(min = 2, max = 100, message = "Name length must be between 2 and 100 characters")
    private String name;


    private FoodCategory category;

    @Size(max = 100, message = "Subcategory length must be less than or equal to 100 characters")
    private String subCategory;


    private Double price;

    private Long restaurantId;

    @Size(max = 255, message = "Image URL length must be less than or equal to 255 characters")
    private String imageUrl;


    private Long calories;

    @Size(max = 255, message = "Description length must be less than or equal to 255 characters")
    private String description;
}

package com.maverix.makeatable.dto.Food;

import com.maverix.makeatable.enums.FoodCategory;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class FoodGetDto {
    @NotNull(message = "ID cannot be null")
    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 100, message = "Name length must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Category cannot be null")
    private FoodCategory category;

    @Size(max = 100, message = "Subcategory length must be less than or equal to 100 characters")
    private String subCategory;

    private Double price;

    @NotNull(message = "Restaurant ID cannot be null")
    private Long restaurantId;


    private Double rating;


    private String imageUrl;

    private Long calories;

    @Size(max = 255, message = "Description length must be less than or equal to 255 characters")
    private String description;
}

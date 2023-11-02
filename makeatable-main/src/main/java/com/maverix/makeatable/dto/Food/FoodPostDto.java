package com.maverix.makeatable.dto.Food;

import com.maverix.makeatable.enums.FoodCategory;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class FoodPostDto {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 100, message = "Name length must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Category cannot be null")
    private FoodCategory category;

    private String subCategory;

    @NotNull(message = "Price cannot be null")
    private Double price;

    @Size(max = 255, message = "Image URL length must be less than or equal to 255 characters")
    private String imageUrl;

    @NotNull(message = "Calories cannot be null")
    private Long calories;

    @Size(max = 255, message = "Description length must be less than or equal to 255 characters")
    private String description;
}

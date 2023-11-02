package com.maverix.makeatable.dto.Restaurent;

import com.maverix.makeatable.enums.FoodCategory;
import lombok.Data;

@Data
public class RestaurantFilterDto {
    private String Fullname;
    private String location;
    private FoodCategory foodType;
    public void setFoodCategory(FoodCategory foodCategory) {
        this.foodType = foodCategory;
    }
}


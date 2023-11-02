package com.maverix.makeatable.dto.Restaurent;


import com.maverix.makeatable.enums.FoodCategory;
import com.maverix.makeatable.enums.RoomType;
import lombok.Data;

import java.time.LocalTime;

@Data
public class RestaurantPutDto {
    private String fullName;
    private Long mobileNum;
    private String location;
    private String email;
    private String imageUrl;
    private LocalTime openTime;
    private LocalTime closeTime;
    private FoodCategory foodType;
    private Long seatNum;
    private String description;
    private RoomType typeRoom;
}

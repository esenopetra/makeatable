package com.maverix.makeatable.dto.Orders;

import com.maverix.makeatable.enums.FoodCategory;
import com.maverix.makeatable.enums.RoomType;
import lombok.Data;

@Data
public class OrderDetailsDTO {
    private RoomType roomType;
    private int numberOfSeats;
    private String userName;
}
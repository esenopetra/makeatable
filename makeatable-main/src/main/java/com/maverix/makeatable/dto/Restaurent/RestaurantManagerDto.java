package com.maverix.makeatable.dto.Restaurent;
import com.maverix.makeatable.enums.FoodCategory;
import com.maverix.makeatable.enums.RoomType;
import lombok.Data;

@Data
public class RestaurantManagerDto {
    private Long id;
    private String fullName;
    private Long mobileNum;
    private String location;
    private String email;
    private String imageUrl;
    private FoodCategory foodType;
    private String description;
    private RoomType typeRoom;
    private Double averageRating;
}

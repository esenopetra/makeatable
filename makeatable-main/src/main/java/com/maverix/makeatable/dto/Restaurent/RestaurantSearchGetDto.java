package com.maverix.makeatable.dto.Restaurent;

import lombok.Data;

@Data
public class RestaurantSearchGetDto {
    private Long id;
    private String fullName;
    private String description;
    private String location;
}

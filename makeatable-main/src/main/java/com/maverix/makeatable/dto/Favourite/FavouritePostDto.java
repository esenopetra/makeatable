package com.maverix.makeatable.dto.Favourite;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FavouritePostDto {
    @NotNull(message = "ID cannot be null")
    private Long id;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Restaurant ID cannot be null")
    private Long restaurantId;
}



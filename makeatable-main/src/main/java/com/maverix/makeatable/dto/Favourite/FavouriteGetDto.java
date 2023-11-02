package com.maverix.makeatable.dto.Favourite;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class FavouriteGetDto {
    @NotNull(message = "ID cannot be null")
    private Long id;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Restaurant ID cannot be null")
    private Long restaurantId;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}

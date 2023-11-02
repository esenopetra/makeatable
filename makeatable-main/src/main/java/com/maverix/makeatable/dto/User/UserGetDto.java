package com.maverix.makeatable.dto.User;

import com.maverix.makeatable.enums.FoodCategory;
import com.maverix.makeatable.enums.UserType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserGetDto {
    private Long id;
    private String fullName;
    private String email;
    private String mobileNum;
    private FoodCategory preference;
    private UserType userType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.maverix.makeatable.dto.User;

import com.maverix.makeatable.enums.FoodCategory;
import com.maverix.makeatable.enums.UserType;
import lombok.Data;


@Data
public class ManagerProfileDto {
    private Long id;
    private String fullName;
    private String email;
    private String imageUrl;
    private String mobileNum;
    private FoodCategory preference;
    private UserType userType;
}

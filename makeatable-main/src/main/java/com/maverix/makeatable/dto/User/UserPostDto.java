package com.maverix.makeatable.dto.User;



import com.maverix.makeatable.enums.FoodCategory;
import com.maverix.makeatable.enums.UserType;
import lombok.Data;

@Data
public class UserPostDto {
    private String fullName;
    private String email;
    private String password;
    private String mobileNum;
    private FoodCategory preference;
    private UserType userType;
}

package com.maverix.makeatable.dto.User;

import com.maverix.makeatable.enums.FoodCategory;
import com.maverix.makeatable.enums.UserType;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserPutDto {

    @Size(max = 100, message = "Full name length must be less than or equal to 100 characters")
    private String fullName;


    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email length must be less than or equal to 100 characters")
    private String email;


    @Size(min = 8, max = 100, message = "Password length must be between 8 and 100 characters")
    private String password;


    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits long")
    private String mobileNum;


    private FoodCategory preference;


    private UserType userType;
}

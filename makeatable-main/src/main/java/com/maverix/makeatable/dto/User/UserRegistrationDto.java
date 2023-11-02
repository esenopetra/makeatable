package com.maverix.makeatable.dto.User;

import com.maverix.makeatable.enums.FoodCategory;
import com.maverix.makeatable.enums.UserStatus;
import com.maverix.makeatable.enums.UserType;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserRegistrationDto {
    @NotBlank(message = "Full name cannot be blank")
    @Size(max = 100, message = "Full name length must be less than or equal to 100 characters")
    private String fullName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email length must be less than or equal to 100 characters")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 100, message = "Password length must be between 8 and 100 characters")
    private String password;

    @NotBlank(message = "Mobile number cannot be blank")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits long")
    private String mobileNumber;

    @NotNull(message = "Preference cannot be null")
    private FoodCategory preference;

    @NotNull(message = "User type cannot be null")
    private UserType userType;

    @NotNull(message = "Status cannot be null")
    private UserStatus status;
}

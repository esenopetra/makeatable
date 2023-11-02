package com.maverix.makeatable.dto.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequestDTO {
    private String newPassword;
}
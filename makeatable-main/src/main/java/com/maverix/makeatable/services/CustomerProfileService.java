package com.maverix.makeatable.services;

import com.maverix.makeatable.dto.User.UserGetDto;
import com.maverix.makeatable.dto.User.CustomerProfileUpdateDto;
import com.maverix.makeatable.enums.FoodCategory;
import com.maverix.makeatable.enums.UserType;
import com.maverix.makeatable.exceptions.UserNotFoundException;
import com.maverix.makeatable.models.User;
import com.maverix.makeatable.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomerProfileService {

    private final UserRepository userRepository;

    public CustomerProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserGetDto getUserProfile(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getUserType() == UserType.CUSTOMER) {
                return convertToGetDto(user);
            } else {
                throw new UserNotFoundException("Access denied. User is not of type Customer.");
            }
        } else {
            throw new UserNotFoundException("User not found for id: " + id);
        }
    }

    public void updateUserProfile(String email, CustomerProfileUpdateDto updateDto) {
        User user = userRepository.getByEmail(email);
        if (user != null && user.getUserType() == UserType.CUSTOMER) {
            user.setFullName(updateDto.getFullName());
            user.setPreference(FoodCategory.valueOf(updateDto.getPreference()));
            user.setImageUrl(updateDto.getImageUrl());
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Access denied. User not found or not of type Customer.");
        }
    }

    private UserGetDto convertToGetDto(User user) {
        UserGetDto userGetDto = new UserGetDto();
        BeanUtils.copyProperties(user, userGetDto);
        return userGetDto;
    }
}




package com.maverix.makeatable.services;

import com.maverix.makeatable.dto.User.UserGetDto;
import com.maverix.makeatable.dto.User.UserPostDto;
import com.maverix.makeatable.dto.User.UserPutDto;
import com.maverix.makeatable.exceptions.UserNotFoundException;
import com.maverix.makeatable.models.User;
import com.maverix.makeatable.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserGetDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToGetDto)
                .collect(Collectors.toList());
    }
    public String getIdfromUsername(String username) {
        Long userId = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found for username: " + username))
                .getId();
        return userId.toString();
    }
    public UserGetDto getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(this::convertToGetDto).orElse(null);
    }

    public UserGetDto createUser(UserPostDto userPostDto) {
        User user = convertToEntity(userPostDto);
        User savedUser = userRepository.save(user);
        return convertToGetDto(savedUser);
    }

    public UserGetDto updateUser(Long id, UserPutDto userPutDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            BeanUtils.copyProperties(userPutDto, existingUser);
            existingUser.setUpdatedAt(LocalDateTime.now());
            User updatedUser = userRepository.save(existingUser);
            return convertToGetDto(updatedUser);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserGetDto convertToGetDto(User user) {
        UserGetDto userGetDto = new UserGetDto();
        BeanUtils.copyProperties(user, userGetDto);
        return userGetDto;
    }

    private User convertToEntity(UserPostDto userPostDto) {
        User user = new User();
        BeanUtils.copyProperties(userPostDto, user);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public String getEmailByPasswordResetToken(String token) {
        User user = userRepository.findByUsertoken(token);
        if(user !=null){
            return user.getEmail();
        }
        return null;
    }

    public void createPasswordResetTokenForUser(String email, String resetToken) {
        User user=userRepository.getByEmail(email);
        if(user != null){
            user.setUsertoken(resetToken);
            userRepository.save(user);
        }
        else {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
    }

    public void updatePassword(String email, String newPassword) {
        User user=userRepository.getByEmail(email);
        if(user != null){
            user.setPassword(newPassword);
            user.setUsertoken(null);
            userRepository.save(user);
        }
    }


    public User saveUser(User user) {
       return userRepository.save(user);
    }

    public Optional<User> getUserFullById(Long userId) {
        return userRepository.findById(userId);
    }
}

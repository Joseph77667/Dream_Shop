package com.Joseph.dreamShop.service.user;

import com.Joseph.dreamShop.dto.UserDto;
import com.Joseph.dreamShop.model.User;
import com.Joseph.dreamShop.request.CreateUserRequest;
import com.Joseph.dreamShop.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}

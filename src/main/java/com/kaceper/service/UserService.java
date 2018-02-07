package com.kaceper.service;

import com.kaceper.dto.UserDto;
import com.kaceper.model.User;

public interface UserService {
    User createUser(UserDto userDto);
    User updateUser(UserDto userDto);
}

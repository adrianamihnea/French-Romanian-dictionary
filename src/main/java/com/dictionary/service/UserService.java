package com.dictionary.service;

import com.dictionary.dto.UserDto;
import com.dictionary.model.AuthResult;
import com.dictionary.model.RegistrationRequest;
import com.dictionary.model.User;

import java.util.List;


public interface UserService {

    boolean checkEmail(String email);

    UserDto registerUser(RegistrationRequest registrationRequest);

    AuthResult authenticate(String username, String password);

    UserDto getLoginUser();

    UserDto getUserById(Integer id);

    List<UserDto> getAllUsers();

    UserDto createUser(User user);

    UserDto updateUser(User user);

    void deleteUser(User user);

    boolean isRegistered(String username);
}

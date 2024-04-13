package com.dictionary.service.impl;

import com.dictionary.dto.UserDto;
import com.dictionary.mapper.UserMapper;
import com.dictionary.model.RegistrationRequest;
import com.dictionary.model.User;
import com.dictionary.repository.UserRepository;
import com.dictionary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public boolean checkEmail(String email) {
        return userRepository.existsByEmailAddress(email);
    }

    @Override
    public UserDto registerUser(RegistrationRequest registrationRequest) {
        User user = userMapper.registrationRequestToUser(registrationRequest);
        return userMapper.userEntityToDto(userRepository.save(user));
    }

    @Override
    public UserDto getLoginUser() {
        // Implement logic to get the currently logged-in user
        // This might involve using Spring Security's Authentication object or similar mechanisms
        return null;
    }

    @Override
    public UserDto getUserById(Integer id) {
        return userMapper.userEntityToDto(userRepository.findById(id).orElse(null));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.userListEntityToDto(userRepository.findAll());
    }

    @Override
    public UserDto createUser(User user) {
        return userMapper.userEntityToDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(User user) {
        return userMapper.userEntityToDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}

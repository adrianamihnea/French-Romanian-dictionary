package com.dictionary.service.impl;

import com.dictionary.config.MySecurityAuthentication;
import com.dictionary.config.MySecurityUser;
import com.dictionary.dto.UserDto;
import com.dictionary.mapper.UserMapper;
import com.dictionary.model.AuthResult;
import com.dictionary.model.RegistrationRequest;
import com.dictionary.model.User;
import com.dictionary.repository.UserRepository;
import com.dictionary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public AuthResult authenticate(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                // Convert User entity to UserDto
                UserDto userDto = userMapper.userEntityToDto(user);

                // Convert UserDto to MySecurityUser
                MySecurityUser mySecurityUser = MySecurityUser.fromUserDto(userDto);

                // Create an instance of MySecurityAuthentication with authenticated status
                MySecurityAuthentication authentication = MySecurityAuthentication.authenticated(mySecurityUser);

                // Update the security context with the authenticated authentication object
                SecurityContextHolder.getContext().setAuthentication(authentication);

                return AuthResult.SUCCESS; // Authentication successful
            } else {
                return AuthResult.BAD_CREDENTIALS; // Incorrect password
            }
        } else {
            return AuthResult.UNREGISTERED_USER; // User not registered
        }
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

    @Override
    public boolean isRegistered(String username) {
        return userRepository.findByUsername(username).isPresent();
    }


}

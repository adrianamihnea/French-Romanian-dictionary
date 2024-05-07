package com.dictionary.controller;

import com.dictionary.dto.UserDto;
import com.dictionary.model.AuthResult;
import com.dictionary.model.RegistrationRequest;
import com.dictionary.model.User;
import com.dictionary.model.UserInfo;
import com.dictionary.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String home(Model model, Authentication authentication){
        if(authentication != null){
            UserDto userDto = userService.getLoginUser();
            model.addAttribute("user", userDto);
        }
        model.addAttribute("title", "Home");

        return "login";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("title", "Login");

        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestParam String username, @RequestParam String password, HttpSession session) {
        AuthResult authResult = userService.authenticate(username, password);
        if (authResult == AuthResult.SUCCESS) {
            session.setAttribute("username", username);
            return ResponseEntity.ok().build(); // Return 200 OK for successful login
        } else if (authResult == AuthResult.BAD_CREDENTIALS) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } else if (authResult == AuthResult.UNREGISTERED_USER) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not registered");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error");
        }
    }

    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("title", "Login");
        model.addAttribute("loginError", true);

        return "login";
    }

    @GetMapping("/register")
    public String register(@RequestParam(value="registrationSuccess", required = false) String success, Model model){
        model.addAttribute("title", "Register");
        model.addAttribute("registrationSuccess", success);
        model.addAttribute("user", new RegistrationRequest());

        return "register";
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        try {
            userService.logout();

            return ResponseEntity.ok("Logout successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to logout: " + e.getMessage());
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody RegistrationRequest registrationRequest) {
        try {
            UserDto userDto = userService.registerUser(registrationRequest);
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user: " + e.getMessage());
        }
    }

    @GetMapping("/users")
    public List<UserInfo> getUsersInfo() {
        List<User> users = userService.getAllUsers();
        return users.stream()
                .map(user -> new UserInfo(user.getUsername(), user.getLoggedIn()))
                .collect(Collectors.toList());
    }

    // Endpoint to get user type by username
    @GetMapping("/user-type")
    public ResponseEntity<String> getUserTypeByUsername() {
        UserDto userDto = userService.getLoginUser();
        String username = userDto.username();
        String userType = userService.getUserTypeByUsername(username);
        if (userType != null) {
            return ResponseEntity.ok(userType);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

}

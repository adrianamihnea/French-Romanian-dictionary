package com.dictionary.controller;

import com.dictionary.dto.UserDto;
import com.dictionary.model.AuthResult;
import com.dictionary.model.RegistrationRequest;
import com.dictionary.model.User;
import com.dictionary.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
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

//    @GetMapping("/login")
//    public String login(Model model){
//        model.addAttribute("title", "Login");
//
//        return "login";
//    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password, HttpSession session) {
        AuthResult authResult = userService.authenticate(username, password);
        if (authResult == AuthResult.SUCCESS) {
            session.setAttribute("username", username);
            return "redirect:/words";
        } else if (authResult == AuthResult.BAD_CREDENTIALS) {
            return "redirect:/login-error?message=Invalid credentials"; // Redirect with error message
        } else if (authResult == AuthResult.UNREGISTERED_USER) {
            return "redirect:/login-error?message=User not registered"; // Redirect with error message
        } else {
            return "redirect:/login-error?message=Unknown error"; // Redirect with error message
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

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute("user") RegistrationRequest registrationRequest, RedirectAttributes redirectAttributes){

        UserDto userDto = userService.registerUser(registrationRequest);

        redirectAttributes.addAttribute("registrationSuccess", "Success");

        return "redirect:/register";
    }

}

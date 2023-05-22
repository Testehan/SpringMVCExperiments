package com.testehan.SpringMVCExperiments.controller;

import com.testehan.SpringMVCExperiments.dto.UserRegistrationDTO;
import com.testehan.SpringMVCExperiments.model.UserEntity;
import com.testehan.SpringMVCExperiments.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model){
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        model.addAttribute("user",userRegistrationDTO);

        return "register";
    }

    @GetMapping("/login")
    public String getLoginForm(Model model){
        return "login";
    }

    @PostMapping("/register/save")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDTO user,
                               BindingResult result, Model model){
        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());
        if (existingUserEmail != null && existingUserEmail.getEmail()!=null && !existingUserEmail.getEmail().isEmpty()){
            // we put in the message from below username/email because of security reasons...we don't want attackers to know
            // which email is used on what websites
//            result.rejectValue("email","There is an existing user with that username/email");
            return "redirect:/register?fail";
        }

        UserEntity existingUserEntity = userService.findByUsername(user.getUsername());
        if (existingUserEntity != null && existingUserEntity.getUsername()!=null && !existingUserEntity.getUsername().isEmpty()){
            // we put in the message from below username/email because of security reasons...we don't want attackers to know
            // which email is used on what websites
//            result.rejectValue("username","There is an existing user with that username/email");
            return "redirect:/register?fail";
        }
        if (result.hasErrors()){
            model.addAttribute("user",user);
            return "register";
        }

        userService.saveUser(user);

        return "redirect:/clubs?success";
    }
}

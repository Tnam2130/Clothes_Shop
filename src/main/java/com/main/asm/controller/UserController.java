package com.main.asm.controller;

import com.main.asm.entity.UserDto;
import com.main.asm.entity.Users;
import com.main.asm.repository.UsersRepository;
import com.main.asm.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    UsersRepository userRepository;

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        model.addAttribute("title", "Đăng nhập");
        return "users/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user=new UserDto();
        model.addAttribute("title", "Đăng ký");
        model.addAttribute("user", user);
        return "users/register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
        Users existingUser=userService.findByEmail(user.getEmail());
        System.out.println(user.getEmail());
        if (existingUser != null){
            result.rejectValue("email",null,
                    "Email đã được đăng ký!");
        }
        if(result.hasErrors()){
            model.addAttribute("user",user);
            return "/users/register";
        }
        userService.saveUser(user);
        return "redirect:/login?success";
    }

//    Xử lý danh sách người dùng
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users= userService.findAllUsers();
        model.addAttribute("users",users);
        model.addAttribute("title","Danh sách người dùng");
        return "/users/userList";
    }
}

package com.main.asm.controller;

import com.main.asm.entity.UserDto;
import com.main.asm.entity.Users;
import com.main.asm.repository.UsersRepository;
import com.main.asm.security.CustomUserDetailsService;
import com.main.asm.service.EmailService;
import com.main.asm.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    UsersRepository userRepository;

    @Autowired
    EmailService emailService;
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        model.addAttribute("title", "Đăng nhập");
        return "users/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        customUserDetailsService.logout(request, response);
        return "redirect:/login"; // Chuyển hướng người dùng về trang đăng nhập sau khi logout
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("title", "Đăng ký");
        model.addAttribute("user", user);
        return "users/register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
        Users existingUser = userService.findByEmail(user.getEmail());
        System.out.println(user.getEmail());
        if (existingUser != null) {
            result.rejectValue("email", null,
                    "Email đã được đăng ký!");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "/users/register";
        }
        userService.saveUser(user);
        return "redirect:/login?success";
    }

    //    Xử lý danh sách người dùng
    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("title", "Danh sách người dùng");
        return "/users/userList";
    }


    @GetMapping("/send-code")
    public String sendCode() {
        return "/users/sendCode";
    }

    @PostMapping("/do-sendCode")
    public String doSendCode(@ModelAttribute("username") String username) {
        Users existUsername = userService.findByEmail(username);

        if (existUsername != null) {
            emailService.sendCode(username);
            return "redirect:/check-code?username=" + username;
        } else {
            return "redirect:/send-code?error";
        }
    }

    @GetMapping("/check-code")
    public String checkCode(Model model, @RequestParam(name = "username", defaultValue = "") String username) {
        model.addAttribute("username", username);
        return "users/checkCode";
    }

    @PostMapping("/do-checkCode")
    public String doCheckCode(@ModelAttribute("username") String username, @ModelAttribute("code") String code) {
        Users users = emailService.getUserByCode(code);
        if (users != null) {
            return "redirect:/resetPassword?username=" + username;
        } else {
            return "redirect:/check-code?error";
        }
    }

    @GetMapping("/resetPassword")
    public String resetPassword(Model model, @RequestParam(name = "username") String username) {
        model.addAttribute("username", username);
        return "users/resetPassword";
    }

    @PostMapping("/do-resetPassword")
    public String doResetPassword(@ModelAttribute("username") String username, @ModelAttribute("password1") String newPassword, @ModelAttribute("password2") String repeatPassword) {
        if (repeatPassword.equals(newPassword)) {
            userService.resetPassword(username, repeatPassword);
            System.out.println("ok");
            return "redirect:/login";
        } else {
            System.out.println("no ok");
            return "redirect:/resetPassword?username=" + username + "?error";
        }
    }

    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal) {
        String userEmail= principal.getName();
        System.out.println(userEmail+" hello");
        model.addAttribute("user",userService.findByEmail(userEmail));
        return "users/profile";
    }

    @RequestMapping("/user/update")
    public String update(@ModelAttribute("user") Users user, @ModelAttribute("username") String username,
                         @ModelAttribute("email") String email,
                         @ModelAttribute("address") String address
    ) {
        Users existUser = userService.findByEmail(email);
        existUser.setUsername(username);
        existUser.setAddress(address);
        userRepository.save(existUser);

        return "redirect:/index?" + user.getUsername();
    }


}

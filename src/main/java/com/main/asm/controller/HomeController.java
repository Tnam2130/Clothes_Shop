package com.main.asm.controller;

import com.main.asm.entity.UserDto;
import com.main.asm.entity.Users;
import com.main.asm.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @RequestMapping({"/" ,"/home" ,"/index"})
    public String index(Model model){
        model.addAttribute("title","DEGREY - DEGREY VIETNAM");

        return "index";
    }

}

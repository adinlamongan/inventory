package com.adn.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {

//    @Autowired
//    private PasswordEncoder passwordEncoder;


    @RequestMapping("/login")
    public String login() {
        //System.out.println(passwordEncoder.encode("codejava"));

        return "login";
    }

    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }



    @RequestMapping("/")
    public String home() {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        CustomUser   user = (CustomUser) securityContext.getAuthentication().getPrincipal();
//        int id = user.getUserId();
//        System.out.println("zzzzz"+id);
        return "home";
    }
}

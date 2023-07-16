package com.adn.inventory.controller;

import com.adn.inventory.models.AppUserQuery;
import com.adn.inventory.models.Role;
import com.adn.inventory.models.Supplier;
import com.adn.inventory.repository.RoleRepo;
import com.adn.inventory.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private RoleRepo roleRepo;

    @GetMapping("/user")
    public String detail(Model model) {
        model.addAttribute("title", "User");

        return "user/user-tabel";
    }

    @GetMapping("/user/detail/{id}")
    public String detail(Model model, @PathVariable("id") int id) {
        AppUserQuery data = appUserService.getAppUserByid(id);
        model.addAttribute("title", "User");
        model.addAttribute("data", data);
        return "user/user-detail";
    }

    @GetMapping("/user/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        AppUserQuery data = appUserService.getAppUserByid(id);
        List<Role> roleList = (List<Role>) roleRepo.findAll();
        model.addAttribute("title", "User");
        model.addAttribute("data", data);
        model.addAttribute("role_list", roleList);
        return "user/user-edit";
    }

    @GetMapping("/user/add")
    public String edit(Model model) {
        List<Role> roleList = (List<Role>) roleRepo.findAll();
        model.addAttribute("role_list", roleList);
        model.addAttribute("title", "User");
        return "user/user-add";
    }


}

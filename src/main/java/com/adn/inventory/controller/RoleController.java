package com.adn.inventory.controller;

import com.adn.inventory.dto.GroupMenuResponseDTO;
import com.adn.inventory.models.Role;
import com.adn.inventory.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/role")
    public String detail(Model model) {
        model.addAttribute("title", "Role");
        return "role/role-tabel";
    }

    @GetMapping("/role/detail/{id}")
    public String detail(Model model, @PathVariable("id") int id) {
        Role role = roleService.getRoleByid(id);
        model.addAttribute("title", "Role");
        model.addAttribute("data", role);
        return "role/role-detail";
    }

    @GetMapping("/role/setting/{id}")
    public String setting(Model model, @PathVariable("id") int id) {
        Role role = roleService.getRoleByid(id);
        List<GroupMenuResponseDTO> menuSubResponseDTO = roleService.getMenuSub(id);
        model.addAttribute("title", "Role");
        model.addAttribute("menu_sub", menuSubResponseDTO);
        model.addAttribute("data", role);
        return "role/role-setting";
    }

    @GetMapping("/role/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        Role role = roleService.getRoleByid(id);
        model.addAttribute("title", "Role");
        model.addAttribute("data", role);
        return "role/role-edit";
    }

    @GetMapping("/role/add")
    public String edit(Model model) {
        model.addAttribute("title", "Role");

        return "role/role-add";
    }
}

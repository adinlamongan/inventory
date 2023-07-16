package com.adn.inventory.controller;

import com.adn.inventory.dto.JualDQueryResponseDTO;
import com.adn.inventory.dto.JualHQueryResponseDTO;
import com.adn.inventory.services.JualService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class JualController {
    private final JualService jualService;

    @GetMapping("invoice")
    public String tabel(Model model){
        model.addAttribute("title", "Invoice");
        return "jual/jual-tabel";
    }

    @GetMapping("/invoice/add")
    public String add(Model model) {
        model.addAttribute("title", "Invoice");
        return "jual/jual-add";
    }

    @GetMapping("/invoice/detail/{id}")
    public String detail(Model model, @PathVariable("id") int id) {
        JualHQueryResponseDTO header = jualService.getJualH(id);
        List<JualDQueryResponseDTO> detail = jualService.getJualD(id);
        model.addAttribute("title", "Invoice");
        model.addAttribute("data", header);
        model.addAttribute("datadetail", detail.isEmpty() ? null : detail);
        return "jual/jual-detail";
    }

    @GetMapping("/invoice/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        JualHQueryResponseDTO header = jualService.getJualH(id);
        List<JualDQueryResponseDTO> detail = jualService.getJualD(id);
        model.addAttribute("title", "Invoice");
        model.addAttribute("data", header);
        model.addAttribute("datadetail", detail.isEmpty() ? null : detail);
        return "jual/jual-edit";
    }
}

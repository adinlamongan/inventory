package com.adn.inventory.controller;

import com.adn.inventory.dto.BayarDQueryResponseDTO;
import com.adn.inventory.dto.BayarHQueryResponseDTO;
import com.adn.inventory.dto.JualDQueryResponseDTO;
import com.adn.inventory.dto.JualHQueryResponseDTO;
import com.adn.inventory.services.BayarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class BayarController {
    private final BayarService bayarService;

    @GetMapping("pembayaran")
    public String tabel(Model model){
        model.addAttribute("title", "Pembayaran Piutang");
        return "pembayaran/pembayaran-tabel";
    }

    @GetMapping("/pembayaran/add")
    public String add(Model model) {
        model.addAttribute("title", "Pembayaran Piutang");
        return "pembayaran/pembayaran-add";
    }

    @GetMapping("/pembayaran/detail/{id}")
    public String detail(Model model, @PathVariable("id") int id) {
        BayarHQueryResponseDTO header = bayarService.getBayarH(id);
        List<BayarDQueryResponseDTO> detail = bayarService.getBayarD(id);
        model.addAttribute("title", "Pembayaran Piutang");
        model.addAttribute("data", header);
        model.addAttribute("datadetail", detail.isEmpty() ? null : detail);
        return "pembayaran/pembayaran-detail";
    }

    @GetMapping("/pembayaran/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        BayarHQueryResponseDTO header = bayarService.getBayarH(id);
        List<BayarDQueryResponseDTO> detail = bayarService.getBayarD(id);
        model.addAttribute("title", "Pembayaran Piutang");
        model.addAttribute("data", header);
        model.addAttribute("datadetail", detail.isEmpty() ? null : detail);
        return "pembayaran/pembayaran-edit";
    }
}

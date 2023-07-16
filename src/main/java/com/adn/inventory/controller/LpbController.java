package com.adn.inventory.controller;

import com.adn.inventory.dto.LpbDQueryResponseDTO;
import com.adn.inventory.dto.LpbHQueryResponseDTO;
import com.adn.inventory.services.LpbService;
import com.adn.inventory.models.Gudang;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class LpbController {
    private final LpbService lpbService;

    @GetMapping("lpb")
    public String tabel(Model model){
        model.addAttribute("title", "Laporan Penerimaan Barang");
        return "lpb/lpb-tabel";
    }

    @GetMapping("/lpb/add")
    public String add(Model model) {
        model.addAttribute("title", "Laporan Penerimaan Barang");
        return "lpb/lpb-add";
    }

    @GetMapping("/lpb/detail/{id}")
    public String detail(Model model, @PathVariable("id") int id) {
        LpbHQueryResponseDTO header = lpbService.getLpbH(id);
        List<LpbDQueryResponseDTO> detail = lpbService.getLpbD(id);
        model.addAttribute("title", "Laporan Penerimaan Barang");
        model.addAttribute("data", header);
        model.addAttribute("datadetail", detail.isEmpty() ? null : detail);
        return "lpb/lpb-detail";
    }

    @GetMapping("/lpb/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        LpbHQueryResponseDTO header = lpbService.getLpbH(id);
        List<LpbDQueryResponseDTO> detail = lpbService.getLpbD(id);
        List<Gudang> gudangList = lpbService.getListGudang();
        model.addAttribute("title", "Laporan Penerimaan Barang");
        model.addAttribute("data", header);
        model.addAttribute("datadetail", detail.isEmpty() ? null : detail);
        model.addAttribute("listgudang", gudangList);
        return "lpb/lpb-edit";
    }
}

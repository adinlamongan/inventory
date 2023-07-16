package com.adn.inventory.controller;

import com.adn.inventory.dto.LpbDQueryResponseDTO;
import com.adn.inventory.dto.LpbHQueryResponseDTO;
import com.adn.inventory.dto.SuratJalanDQueryResponseDTO;
import com.adn.inventory.dto.SuratJalanHQueryResponseDTO;
import com.adn.inventory.models.Gudang;
import com.adn.inventory.services.SuratJalanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class SuratJalanController {
    private final SuratJalanService suratJalanService;

    @GetMapping("surat_jalan")
    public String tabel(Model model){
        model.addAttribute("title", "Surat Jalan");
        return "surat_jalan/surat_jalan-tabel";
    }

    @GetMapping("/surat_jalan/add")
    public String add(Model model) {
        model.addAttribute("title", "Surat Jalan");
        return "surat_jalan/surat_jalan-add";
    }

    @GetMapping("/surat_jalan/detail/{id}")
    public String detail(Model model, @PathVariable("id") int id) {
        SuratJalanHQueryResponseDTO header = suratJalanService.getSuratJalanH(id);
        List<SuratJalanDQueryResponseDTO> detail = suratJalanService.getSuratJalanD(id);
        model.addAttribute("title", "Surat Jalan");
        model.addAttribute("data", header);
        model.addAttribute("datadetail", detail.isEmpty() ? null : detail);
        return "surat_jalan/surat_jalan-detail";
    }

    @GetMapping("/surat_jalan/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        SuratJalanHQueryResponseDTO header = suratJalanService.getSuratJalanH(id);
        List<SuratJalanDQueryResponseDTO> detail = suratJalanService.getSuratJalanD(id);
        List<Gudang> gudangList = suratJalanService.getListGudang();
        model.addAttribute("title", "Surat Jalan");
        model.addAttribute("data", header);
        model.addAttribute("datadetail", detail.isEmpty() ? null : detail);
        model.addAttribute("listgudang", gudangList);
        return "surat_jalan/surat_jalan-edit";
    }
}

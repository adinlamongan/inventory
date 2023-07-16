package com.adn.inventory.controller;

import com.adn.inventory.dto.PindahBarangDQueryResponseDTO;
import com.adn.inventory.dto.PindahBarangHQueryResponseDTO;
import com.adn.inventory.models.Gudang;
import com.adn.inventory.services.PindahBarangService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class PindahBarangController {
    private final PindahBarangService pindahBarangService;

    @GetMapping("pindah_barang")
    public String tabel(Model model){
        model.addAttribute("title", "Pindah Barang");
        return "pindah_barang/pindah_barang-tabel";
    }

    @GetMapping("/pindah_barang/add")
    public String add(Model model) {
        List<Gudang> gudangList = pindahBarangService.getListGudang();
        model.addAttribute("title", "Pindah Barang");
        model.addAttribute("listgudang", gudangList);
        return "pindah_barang/pindah_barang-add";
    }

    @GetMapping("/pindah_barang/detail/{id}")
    public String detail(Model model, @PathVariable("id") int id) {
        PindahBarangHQueryResponseDTO header = pindahBarangService.getPindahBarangH(id);
        List<PindahBarangDQueryResponseDTO> detail = pindahBarangService.getPindahBarangD(id);
        model.addAttribute("title", "Pindah Barang");
        model.addAttribute("data", header);
        model.addAttribute("datadetail", detail.isEmpty() ? null : detail);
        return "pindah_barang/pindah_barang-detail";
    }

    @GetMapping("/pindah_barang/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        PindahBarangHQueryResponseDTO header = pindahBarangService.getPindahBarangH(id);
        List<PindahBarangDQueryResponseDTO> detail = pindahBarangService.getPindahBarangD(id);
        List<Gudang> gudangList = pindahBarangService.getListGudang();
        model.addAttribute("title", "Pindah Barang");
        model.addAttribute("data", header);
        model.addAttribute("datadetail", detail.isEmpty() ? null : detail);
        model.addAttribute("listgudang", gudangList);
        return "pindah_barang/pindah_barang-edit";
    }
}

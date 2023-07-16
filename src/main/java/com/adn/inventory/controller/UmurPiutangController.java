package com.adn.inventory.controller;

import com.adn.inventory.dto.CustomerResponseDTO;
import com.adn.inventory.dto.UmurPiutangQueryResponseDTO;
import com.adn.inventory.models.Gudang;
import com.adn.inventory.services.CustomerService;
import com.adn.inventory.services.KartuStokService;
import com.adn.inventory.services.PindahBarangService;
import com.adn.inventory.services.UmurPiutangService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class UmurPiutangController {

    private final UmurPiutangService umurPiutangService;

    @GetMapping("umur_piutang")
    public String tabel(Model model){
        model.addAttribute("title", "Laporan Umur Piutang");
        return "umur_piutang/umur_piutang-add";
    }



}

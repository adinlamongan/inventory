package com.adn.inventory.controller;

import com.adn.inventory.dto.KartuStokResponseDTO;
import com.adn.inventory.models.Gudang;
import com.adn.inventory.services.KartuStokService;
import com.adn.inventory.services.PindahBarangService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class KartuStokController {
    private final PindahBarangService pindahBarangService;
    

    private final KartuStokService kartuStokService;

    @GetMapping("kartu_stok")
    public String tabel(Model model){
        List<Gudang> gudangList = pindahBarangService.getListGudang();
        model.addAttribute("title", "Kartu Stok");
        model.addAttribute("listgudang", gudangList);
        return "kartu_stok/kartu_stok-add";
    }




    @GetMapping("kartu_stok/print_data")
    public String printData(Model model) {
        List<KartuStokResponseDTO> data = kartuStokService.getData(KartuStokRestController.getCopyDTO());
        model.addAttribute("data", data);
        model.addAttribute("header", KartuStokRestController.getCopyDTO());
        return "kartu_stok/kartu_stok-print";
    }








}

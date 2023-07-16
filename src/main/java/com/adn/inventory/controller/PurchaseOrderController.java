package com.adn.inventory.controller;

import com.adn.inventory.dto.PODQueryResponseDTO;
import com.adn.inventory.dto.POHQueryResponseDTO;
import com.adn.inventory.services.PurchaseOrderService;
import com.adn.inventory.util.Akses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
@AllArgsConstructor
public class PurchaseOrderController {


    private final PurchaseOrderService purchaseOrderService;

    @Autowired
    private Akses akses;

    @GetMapping("purchase_order")
    public String tabel(Model model){
        if (!akses.cek(model)) { return "403"; }
        model.addAttribute("title", "Purchase Order");
        return "purchase_order/purchase_order-tabel";
    }

    @GetMapping("/purchase_order/add")
    public String add(Model model) {
        if (!akses.cek(model)) { return "403"; }
        model.addAttribute("title", "Purchase Order");
        return "purchase_order/purchase_order-add";
    }

    @GetMapping("/purchase_order/detail/{id}")
    public String detail(Model model, @PathVariable("id") int id) {
        if (!akses.cek(model)) { return "403"; }
        POHQueryResponseDTO header = purchaseOrderService.getPurchaseOrderH(id);
        List<PODQueryResponseDTO> detail = purchaseOrderService.getPurchaseOrderD(id);
        model.addAttribute("title", "Purchase Order");
        model.addAttribute("data", header);
        model.addAttribute("datadetail", detail.isEmpty() ? null : detail);
        return "purchase_order/purchase_order-detail";
    }

    @GetMapping("/purchase_order/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        if (!akses.cek(model)) { return "403"; }
        POHQueryResponseDTO header = purchaseOrderService.getPurchaseOrderH(id);
        List<PODQueryResponseDTO> detail = purchaseOrderService.getPurchaseOrderD(id);
        model.addAttribute("title", "Purchase Order");
        model.addAttribute("data", header);
        model.addAttribute("datadetail", detail.isEmpty() ? null : detail);
        return "purchase_order/purchase_order-edit";
    }

   

}

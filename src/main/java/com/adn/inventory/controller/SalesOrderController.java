package com.adn.inventory.controller;

import com.adn.inventory.dto.SODQueryResponseDTO;
import com.adn.inventory.dto.SOHQueryResponseDTO;
import com.adn.inventory.services.SalesOrderService;
import com.adn.inventory.util.Akses;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
@AllArgsConstructor
public class SalesOrderController {


    private final SalesOrderService salesOrderService;

    @Autowired
    private Akses akses;

    @GetMapping("sales_order")
    public String tabel(Model model){
        if (!akses.cek(model)) { return "403"; }
        model.addAttribute("title", "Sales Order");
        return "sales_order/sales_order-tabel";
    }

    @GetMapping("/sales_order/add")
    public String add(Model model) {
        if (!akses.cek(model)) { return "403"; }
        model.addAttribute("title", "Sales Order");
        return "sales_order/sales_order-add";
    }

    @GetMapping("/sales_order/detail/{id}")
    public String detail(Model model, @PathVariable("id") int id) {
        if (!akses.cek(model)) { return "403"; }
        SOHQueryResponseDTO header = salesOrderService.getSalesOrderH(id);
        List<SODQueryResponseDTO> detail = salesOrderService.getSalesOrderD(id);
        model.addAttribute("title", "Sales Order");
        model.addAttribute("data", header);
        model.addAttribute("datadetail", detail.isEmpty() ? null : detail);
        return "sales_order/sales_order-detail";
    }

    @GetMapping("/sales_order/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        if (!akses.cek(model)) { return "403"; }
        SOHQueryResponseDTO header = salesOrderService.getSalesOrderH(id);
        List<SODQueryResponseDTO> detail = salesOrderService.getSalesOrderD(id);
        model.addAttribute("title", "Sales Order");
        model.addAttribute("data", header);
        model.addAttribute("datadetail", detail.isEmpty() ? null : detail);
        return "sales_order/sales_order-edit";
    }

   

}

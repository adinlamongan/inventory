package com.adn.inventory.controller;

import com.adn.inventory.dto.SupplierResponseDTO;
import com.adn.inventory.models.Supplier;
import com.adn.inventory.services.SupplierService;
import com.adn.inventory.util.Akses;
import com.adn.inventory.util.ExcelGenerator;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SupplierController {

    @Autowired
    private SupplierService supplierService;


    @GetMapping("/supplier")
    public String detail(Model model) {
        model.addAttribute("title", "Supplier");
        return "supplier/supplier-tabel";
    }

    @GetMapping("/supplier/detail/{id}")
    public String detail(Model model, @PathVariable("id") int id) {
        Supplier supplier = supplierService.getSupplierByid(id);
        model.addAttribute("title", "Supplier");
        model.addAttribute("data", supplier);
        return "supplier/supplier-detail";
    }

    @GetMapping("/supplier/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        Supplier supplier = supplierService.getSupplierByid(id);
        model.addAttribute("title", "Supplier");
        model.addAttribute("data", supplier);
        return "supplier/supplier-edit";
    }

    @GetMapping("/supplier/add")
    public String edit(Model model) {
        model.addAttribute("title", "Supplier");
        return "supplier/supplier-add";
    }

    @GetMapping("/supplier_excel")
    public void exportIntoExcelFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=student" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<SupplierResponseDTO> listOfStudents = supplierService.getListSupplier("Toko");
        ExcelGenerator generator = new ExcelGenerator(listOfStudents);
        generator.generateExcelFile(response);
    }
}

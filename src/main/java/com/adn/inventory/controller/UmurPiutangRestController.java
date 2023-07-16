package com.adn.inventory.controller;

import com.adn.inventory.dto.*;
import com.adn.inventory.services.CustomerService;
import com.adn.inventory.services.UmurPiutangService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UmurPiutangRestController {


    private final CustomerService customerService;
    private final UmurPiutangService umurPiutangService;


    @GetMapping("umur_piutang/list_customer")
    private ResponseEntity<List<CustomerResponseDTO>> getListSupplierAutocomplete(@RequestParam(name = "search", required = false, defaultValue = "") String keyword) {
        return ResponseEntity.ok().body(customerService.getListCustomer(keyword));
    }

    @GetMapping("umur_piutang/{customerId}")
    private ResponseEntity<List<UmurPiutangQueryResponseDTO>> getData(@PathVariable("customerId") int customerId) {
        return ResponseEntity.ok().body(umurPiutangService.getData(customerId));
    }
}

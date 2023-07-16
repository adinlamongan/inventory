package com.adn.inventory.controller;

import com.adn.inventory.util.ResponseSuccessDTO;
import com.adn.inventory.dto.SupplierRequestDTO;
import com.adn.inventory.models.Supplier;
import com.adn.inventory.services.SupplierService;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class SupplierRestController {

    private SupplierService supplierService;

    private ResponseSuccessDTO successDTO;

    @PostMapping("/supplierdata")
    public DatatabelOutput<Supplier> listData(@RequestBody PagingRequest pagingRequest) {
        return supplierService.getSupplier(pagingRequest);
    }

    @PostMapping("/supplier")
    public ResponseEntity<Object> add(@RequestBody SupplierRequestDTO dto) {
        supplierService.addSupplier(dto);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    @PostMapping("/supplier_add2")
    public int add2(@RequestBody SupplierRequestDTO dto) {
        // return supplierService.addSupplier(dto);
        supplierService.insert();

        return 1;
    }

    @PutMapping("/supplier/{id}")
    public ResponseEntity<Object> edit(@PathVariable("id") int id ,@RequestBody SupplierRequestDTO dto) {
        supplierService.editSupplier(id, dto);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    @DeleteMapping("/supplier/{id}")
    public ResponseEntity<Object> edit(@PathVariable("id") int id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    
}

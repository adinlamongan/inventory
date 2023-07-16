package com.adn.inventory.controller;

import com.adn.inventory.dto.ProdukResponseDTO;
import com.adn.inventory.dto.CustomerResponseDTO;
import com.adn.inventory.dto.SOHQueryResponseDTO;
import com.adn.inventory.dto.SORequestDTO;
import com.adn.inventory.services.ProdukService;
import com.adn.inventory.services.CustomerService;
import com.adn.inventory.services.SalesOrderService;
import com.adn.inventory.util.ResponseSuccessDTO;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class SalesOrderRestController {

    private final SalesOrderService salesOrderService;
    private final CustomerService supplierService;
    private final ProdukService produkService;
    private ResponseSuccessDTO successDTO;


    @PostMapping("sales_order/list_data")
    public DatatabelOutput<SOHQueryResponseDTO> listData(@RequestBody PagingRequest pagingRequest) {
        return salesOrderService.getListSalesOrder(pagingRequest);
    }

    @GetMapping("sales_order/list_customer")
    private ResponseEntity<List<CustomerResponseDTO>> getListSupplierAutocomplete(@RequestParam(name = "search", required = false, defaultValue = "") String keyword) {
        return ResponseEntity.ok().body(supplierService.getListCustomer(keyword));
    }

    @GetMapping("sales_order/list_produk")
    private ResponseEntity<List<ProdukResponseDTO>> getListBarangAutocomplete(
            @RequestParam(name = "search", required = false, defaultValue = "") String keyword) {
        return ResponseEntity.ok().body(produkService.getListBarangAutocomplete(keyword));
    }

    @PostMapping("sales_order")
    public ResponseEntity<Object> add(@RequestBody SORequestDTO dto) {
        salesOrderService.addSalesOrder(dto);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    @PutMapping("sales_order/{id}")
    public ResponseEntity<Object> edit(@PathVariable("id") int id, @RequestBody SORequestDTO dto) {
        salesOrderService.editSalesOrder(id, dto);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    @DeleteMapping("sales_order/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        salesOrderService.deleteSalesOrder(id);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    @PutMapping("sales_order/verifikasi/{id}")
    public ResponseEntity<Object> verifikasi(@PathVariable("id") int id) {
        salesOrderService.verifikasi(id);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    @PutMapping("sales_order/batal_verifikasi/{id}")
    public ResponseEntity<Object> batalVerifikasi(@PathVariable("id") int id) {
        salesOrderService.batalVerifikasi(id);
        return ResponseEntity.ok(successDTO.getMesage());
    }

}

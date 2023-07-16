package com.adn.inventory.controller;

import com.adn.inventory.dto.ProdukResponseDTO;
import com.adn.inventory.dto.POHQueryResponseDTO;
import com.adn.inventory.dto.PORequestDTO;
import com.adn.inventory.dto.SupplierResponseDTO;
import com.adn.inventory.services.ProdukService;
import com.adn.inventory.services.PurchaseOrderService;
import com.adn.inventory.services.SupplierService;
import com.adn.inventory.util.ResponseSuccessDTO;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class PurchaseOrderRestController {

    private final PurchaseOrderService purchaseOrderService;
    private final SupplierService supplierService;
    private final ProdukService produkService;
    private ResponseSuccessDTO successDTO;

    @PostMapping("purchase_order/list_data")
    public DatatabelOutput<POHQueryResponseDTO> listData(@RequestBody PagingRequest pagingRequest) {
        return purchaseOrderService.getListPurchaseOrder(pagingRequest);
    }

    @GetMapping("purchase_order/list_supplier")
    private ResponseEntity<List<SupplierResponseDTO>> getListSupplierAutocomplete(@RequestParam(name = "search", required = false, defaultValue = "") String keyword) {
        return ResponseEntity.ok().body(supplierService.getListSupplier(keyword));
    }

    @GetMapping("purchase_order/list_produk")
    private ResponseEntity<List<ProdukResponseDTO>> getListBarangAutocomplete(
            @RequestParam(name = "search", required = false, defaultValue = "") String keyword) {
        return ResponseEntity.ok().body(produkService.getListBarangAutocomplete(keyword));
    }

    @PostMapping("purchase_order")
    public ResponseEntity<Object> add(@RequestBody PORequestDTO dto) {
        purchaseOrderService.addPurchaseOrder(dto);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    @PutMapping("purchase_order/{id}")
    public ResponseEntity<Object> edit(@PathVariable("id") int id, @RequestBody PORequestDTO dto) {
        purchaseOrderService.editPurchaseOrder(id, dto);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    @DeleteMapping("purchase_order/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        purchaseOrderService.deletePurchaseOrder(id);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    @PutMapping("purchase_order/verifikasi/{id}")
    public ResponseEntity<Object> verifikasi(@PathVariable("id") int id) {
        purchaseOrderService.verifikasi(id);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    @PutMapping("purchase_order/batal_verifikasi/{id}")
    public ResponseEntity<Object> batalVerifikasi(@PathVariable("id") int id) {
        purchaseOrderService.batalVerifikasi(id);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    @PostMapping("purchase_order/update")
    public int updateharga() {
        return purchaseOrderService.updateHarga();
    }

    @PostMapping("purchase_order/update2")
    public int updateharga2() {
        return purchaseOrderService.updateHarga2();
    }
}

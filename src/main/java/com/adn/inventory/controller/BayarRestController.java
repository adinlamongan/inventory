package com.adn.inventory.controller;

import com.adn.inventory.dto.*;
import com.adn.inventory.services.BayarService;
import com.adn.inventory.util.ResponseSuccessDTO;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class BayarRestController {

    private final BayarService bayarService;
    private ResponseSuccessDTO successDTO;

    @PostMapping("pembayaran/list_data")
    public DatatabelOutput<BayarHQueryResponseDTO> listData(@RequestBody PagingRequest pagingRequest) {
        return bayarService.getListBayar(pagingRequest);
    }

    @GetMapping("pembayaran/list_invoice/{id}/{bayarId}")
    private ResponseEntity<List<BayarListInvoiceQueryResponseDTO>> getListInvoice(@PathVariable("id") int id, @PathVariable("bayarId") int bayarId) {
        return ResponseEntity.ok().body(bayarService.getListInvoice(id,bayarId));
    }


    @PostMapping("pembayaran")
    public ResponseEntity<Object> add(@RequestBody BayarRequestDTO dto) {
        bayarService.addJual(dto);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    @PutMapping("pembayaran/{id}")
    public ResponseEntity<Object> edit(@PathVariable("id") int id, @RequestBody BayarRequestDTO dto) {
        bayarService.editBayar(id, dto);
        return ResponseEntity.ok(successDTO.getMesage());

    }

    @DeleteMapping("pembayaran/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        bayarService.deleteBayar(id);
        return ResponseEntity.ok(successDTO.getMesage());

    }

    @PutMapping("pembayaran/verifikasi/{id}")
    public ResponseEntity<Object> verifikasi(@PathVariable("id") int id) {
        bayarService.verifikasi(id);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    @PutMapping("pembayaran/batal_verifikasi/{id}")
    public ResponseEntity<Object> batalVerifikasi(@PathVariable("id") int id) {
        bayarService.batalVerifikasi(id);
        return ResponseEntity.ok(successDTO.getMesage());
    }
}

package com.adn.inventory.controller;

import com.adn.inventory.dto.JualHQueryResponseDTO;
import com.adn.inventory.dto.JualListProdukSJResponseDTO;
import com.adn.inventory.dto.JualListSJQueryResponseDTO;
import com.adn.inventory.dto.JualRequestDTO;
import com.adn.inventory.services.JualService;
import com.adn.inventory.util.ResponseSuccessDTO;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class JualRestController {

    private final JualService jualService;
    private ResponseSuccessDTO successDTO;

    @PostMapping("invoice/list_data")
    public DatatabelOutput<JualHQueryResponseDTO> listData(@RequestBody PagingRequest pagingRequest) {
        return jualService.getListJual(pagingRequest);
    }

    @GetMapping("invoice/list_sj")
    private ResponseEntity<List<JualListSJQueryResponseDTO>> getListSJAutocomplete(@RequestParam(name = "search", required = false, defaultValue = "") String keyword) {
        return ResponseEntity.ok().body(jualService.getListSJ(keyword));
    }

    @GetMapping("invoice/list_produk_sj/{id}")
    private ResponseEntity<List<JualListProdukSJResponseDTO>> getListBarangPO(@PathVariable("id") int id) {
        return ResponseEntity.ok().body(jualService.getListBarangSJ(id));
    }


    @PostMapping("invoice")
    public ResponseEntity<Object> add(@RequestBody JualRequestDTO dto) {
        jualService.addJual(dto);
        return ResponseEntity.ok(successDTO.getMesage());

    }

    @PutMapping("invoice/{id}")
    public ResponseEntity<Object> edit(@PathVariable("id") int id, @RequestBody JualRequestDTO dto) {
        jualService.editJual(id, dto);
        return ResponseEntity.ok(successDTO.getMesage());

    }

    @DeleteMapping("invoice/{id}/{suratJalanId}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id,@PathVariable("suratJalanId") int suratJalanId) {
        jualService.deleteJual(id,suratJalanId);
        return ResponseEntity.ok(successDTO.getMesage());

    }

    @PutMapping("invoice/verifikasi/{id}")
    public ResponseEntity<Object> verifikasi(@PathVariable("id") int id) {
        jualService.verifikasi(id);
        return ResponseEntity.ok(successDTO.getMesage());

    }

    @PutMapping("invoice/batal_verifikasi/{id}")
    public ResponseEntity<Object> batalVerifikasi(@PathVariable("id") int id) {
        jualService.batalVerifikasi(id);
        return ResponseEntity.ok(successDTO.getMesage());

    }
}

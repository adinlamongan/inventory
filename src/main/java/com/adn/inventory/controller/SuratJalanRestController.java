package com.adn.inventory.controller;

import com.adn.inventory.dto.SuratJalanHQueryResponseDTO;
import com.adn.inventory.dto.SuratJalanListSODResponseDTO;
import com.adn.inventory.dto.SuratJalanListSOResponseDTO;
import com.adn.inventory.dto.SuratJalanRequestDTO;
import com.adn.inventory.models.StokGudang;
import com.adn.inventory.services.SuratJalanService;
import com.adn.inventory.util.ResponseSuccessDTO;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class SuratJalanRestController {

    private final SuratJalanService suratJalanService;
    private ResponseSuccessDTO successDTO;

    @PostMapping("surat_jalan/list_data")
    public DatatabelOutput<SuratJalanHQueryResponseDTO> listData(@RequestBody PagingRequest pagingRequest) {
        return suratJalanService.getListSuratJalan(pagingRequest);
    }

    @GetMapping("surat_jalan/list_so")
    private ResponseEntity<List<SuratJalanListSOResponseDTO>> getListSOAutocomplete(@RequestParam(name = "search", required = false, defaultValue = "") String keyword) {
        return ResponseEntity.ok().body(suratJalanService.getListSO(keyword));
    }

    @GetMapping("surat_jalan/list_produk_so/{id}")
    private ResponseEntity<SuratJalanListSODResponseDTO> getListSOD(@PathVariable("id") int id) {
        return ResponseEntity.ok().body(suratJalanService.getListSOD(id));
    }

    @GetMapping("surat_jalan/get_stok/{gudangId}/{produkId}")
    private ResponseEntity<?> getStok(@PathVariable("gudangId") int gudangId, @PathVariable("produkId") int produkId) {
        StokGudang  stokGudang = suratJalanService.getStokGudangByProdukIdAndGudangId(produkId,gudangId);
        Map<String, Object> response = new LinkedHashMap<>();
        if (stokGudang == null) {
            response.put("id",0);
            response.put("qty",0);
        }else {
            response.put("id",stokGudang.getId());
            response.put("qty",stokGudang.getQty());
        }
        return  ResponseEntity.ok(response);

    }

    @PostMapping("surat_jalan")
    public ResponseEntity<Object> add(@RequestBody SuratJalanRequestDTO dto) {
        suratJalanService.addSuratJalan(dto);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    @PutMapping("surat_jalan/{id}")
    public ResponseEntity<Object> edit(@PathVariable("id") int id, @RequestBody SuratJalanRequestDTO dto) {
        suratJalanService.editSuratJalan(id, dto);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    @DeleteMapping("surat_jalan/{id}/{soId}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id,@PathVariable("soId") int soId) {
        suratJalanService.deleteSuratJalan(id,soId);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    @PutMapping("surat_jalan/verifikasi/{id}")
    public ResponseEntity<Object> verifikasi(@PathVariable("id") int id) {
        suratJalanService.verifikasi(id);
        return ResponseEntity.ok(successDTO.getMesage());
    }

    @PutMapping("surat_jalan/batal_verifikasi/{id}")
    public ResponseEntity<Object> batalVerifikasi(@PathVariable("id") int id) {
        suratJalanService.batalVerifikasi(id);
        return ResponseEntity.ok(successDTO.getMesage());
    }
}

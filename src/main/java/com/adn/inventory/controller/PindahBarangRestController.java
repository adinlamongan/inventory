package com.adn.inventory.controller;

import com.adn.inventory.dto.PindahBarangAutocompleteBarangByGudangAsalResponseDTO;
import com.adn.inventory.dto.PindahBarangHQueryResponseDTO;
import com.adn.inventory.dto.PindahBarangRequestDTO;
import com.adn.inventory.services.PindahBarangService;
import com.adn.inventory.util.ResponseSuccessDTO;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class PindahBarangRestController {
    private final PindahBarangService pindahBarangService;
    private ResponseSuccessDTO successDTO;

    @PostMapping("pindah_barang/list_data")
    public DatatabelOutput<PindahBarangHQueryResponseDTO> listData(@RequestBody PagingRequest pagingRequest) {
        return pindahBarangService.getListPindahBarang(pagingRequest);
    }

    @GetMapping("pindah_barang/list_produk")
    private ResponseEntity<List<PindahBarangAutocompleteBarangByGudangAsalResponseDTO>> getListSupplierAutocomplete(@RequestParam(name = "search", required = false, defaultValue = "") String keyword,
                                                                                                                    @RequestParam(name = "gudang_asal", required = false, defaultValue = "0") Integer gudangAsalId) {
        return ResponseEntity.ok().body(pindahBarangService.getListBarangGudangAsal(keyword, gudangAsalId));
    }

    @GetMapping("pindah_barang/list_produk_edit")
    private ResponseEntity<List<PindahBarangAutocompleteBarangByGudangAsalResponseDTO>> getListSupplierAutocompleteEdit(@RequestParam(name = "search", required = false, defaultValue = "") String keyword,
                                                              @RequestParam(name = "gudang_asal", required = false, defaultValue = "0") int gudangAsalId,
                                                              @RequestParam(name = "pindah_barang_id", required = false, defaultValue = "0") int pindahBarangId) {
        return ResponseEntity.ok().body(pindahBarangService.getListBarangGudangAsalEdit(keyword, gudangAsalId, pindahBarangId));
    }


    @PostMapping("pindah_barang")
    public ResponseEntity<Object> add(@RequestBody PindahBarangRequestDTO dto) {
        pindahBarangService.addPindahBarang(dto);
        return ResponseEntity.ok(successDTO.getMesage());

    }

    @PutMapping("pindah_barang/{id}")
    public ResponseEntity<Object> edit(@PathVariable("id") int id, @RequestBody PindahBarangRequestDTO dto) {
        pindahBarangService.editPindahBarang(id, dto);
        return ResponseEntity.ok(successDTO.getMesage());

    }

    @DeleteMapping("pindah_barang/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        pindahBarangService.deletePindahBarang(id);
        return ResponseEntity.ok(successDTO.getMesage());

    }

    @PutMapping("pindah_barang/verifikasi/{id}")
    public ResponseEntity<Object> verifikasi(@PathVariable("id") int id) {
        pindahBarangService.verifikasi(id);
        return ResponseEntity.ok(successDTO.getMesage());

    }

    @PutMapping("pindah_barang/batal_verifikasi/{id}")
    public ResponseEntity<Object> batalVerifikasi(@PathVariable("id") int id) {
        pindahBarangService.batalVerifikasi(id);
        return ResponseEntity.ok(successDTO.getMesage());

    }
}

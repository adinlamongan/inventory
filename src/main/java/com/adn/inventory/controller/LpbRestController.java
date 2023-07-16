package com.adn.inventory.controller;

import com.adn.inventory.dto.LpbHQueryResponseDTO;
import com.adn.inventory.dto.LpbListPODResponseDTO;
import com.adn.inventory.dto.LpbListPOHQueryResponseDTO;
import com.adn.inventory.dto.LpbRequestDTO;
import com.adn.inventory.services.LpbService;
import com.adn.inventory.util.ResponseSuccessDTO;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class LpbRestController {
    private final LpbService lpbService;
    private ResponseSuccessDTO successDTO;


    @PostMapping("lpb/list_data")
    public DatatabelOutput<LpbHQueryResponseDTO> listData(@RequestBody PagingRequest pagingRequest) {
        return lpbService.getListLpb(pagingRequest);
    }

    @GetMapping("lpb/list_po")
    private ResponseEntity<List<LpbListPOHQueryResponseDTO>> getLisPOAutocomplete(@RequestParam(name = "search", required = false, defaultValue = "") String keyword) {
        return ResponseEntity.ok().body(lpbService.getListPO(keyword));
    }

    @GetMapping("lpb/list_produk_po/{id}")
    private ResponseEntity<LpbListPODResponseDTO> getListBarangPO(@PathVariable("id") int id) {
        return ResponseEntity.ok().body(lpbService.getListPOD(id));
    }

    @PostMapping("lpb")
    public ResponseEntity<Object> add(@RequestBody LpbRequestDTO dto) {
        lpbService.addLpb(dto);
        return ResponseEntity.ok(successDTO.getMesage());

    }

    @PutMapping("lpb/{id}")
    public ResponseEntity<Object> edit(@PathVariable("id") int id, @RequestBody LpbRequestDTO dto) {
        lpbService.editLpb(id, dto);
        return ResponseEntity.ok(successDTO.getMesage());

    }

    @DeleteMapping("lpb/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        lpbService.deleteLpb(id);
        return ResponseEntity.ok(successDTO.getMesage());

    }

    @PutMapping("lpb/verifikasi/{id}")
    public ResponseEntity<Object> verifikasi(@PathVariable("id") int id) {
        lpbService.verifikasi(id);
        return ResponseEntity.ok(successDTO.getMesage());

    }

    @PutMapping("lpb/batal_verifikasi/{id}")
    public ResponseEntity<Object> batalVerifikasi(@PathVariable("id") int id) {
        lpbService.batalVerifikasi(id);
        return ResponseEntity.ok(successDTO.getMesage());

    }
}

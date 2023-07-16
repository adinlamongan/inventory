package com.adn.inventory.repository;

import com.adn.inventory.dto.PindahBarangAutocompleteBarangByGudangAsalResponseDTO;
import com.adn.inventory.dto.PindahBarangDQueryResponseDTO;
import com.adn.inventory.dto.PindahBarangHQueryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PindahBarangQueryRepo {
    Page<PindahBarangHQueryResponseDTO> findPindahBarang(String keyword, String sortBy, String direction, Pageable pageable);

    Long getTotalRecord();




}

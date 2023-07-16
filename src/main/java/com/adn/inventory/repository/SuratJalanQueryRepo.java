package com.adn.inventory.repository;

import com.adn.inventory.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SuratJalanQueryRepo {

    Page<SuratJalanHQueryResponseDTO> findSuratJalan(String keyword, String sortBy, String direction, Pageable pageable);

    Long getTotalRecord();



}

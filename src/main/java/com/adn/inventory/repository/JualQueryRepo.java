package com.adn.inventory.repository;

import com.adn.inventory.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JualQueryRepo {
    Page<JualHQueryResponseDTO> findJual(String keyword, String sortBy, String direction, Pageable pageable);

    Long getTotalRecord();


}

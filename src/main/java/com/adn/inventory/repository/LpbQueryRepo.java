package com.adn.inventory.repository;

import com.adn.inventory.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;

import java.util.List;

public interface LpbQueryRepo {
    Page<LpbHQueryResponseDTO> findLpb(String keyword, String sortBy, String direction, Pageable pageable);

    Long getTotalRecord();

}

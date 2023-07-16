package com.adn.inventory.repository;

import com.adn.inventory.dto.SODQueryResponseDTO;
import com.adn.inventory.dto.SOHQueryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SalesOrderQueryRepo {
    Page<SOHQueryResponseDTO> findSalesOrder(String keyword, String sortBy, String direction, Pageable pageable);

    Long getTotalRecord();


}

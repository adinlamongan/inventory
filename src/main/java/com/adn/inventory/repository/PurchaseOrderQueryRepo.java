package com.adn.inventory.repository;

import com.adn.inventory.dto.PODQueryResponseDTO;
import com.adn.inventory.dto.POHQueryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PurchaseOrderQueryRepo {
    Page<POHQueryResponseDTO> findPurchaseOrder(String keyword, String sortBy, String direction, Pageable pageable);

    Long getTotalRecord();


    Boolean cekTerpakai(int id);
}

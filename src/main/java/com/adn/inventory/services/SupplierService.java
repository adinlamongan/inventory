package com.adn.inventory.services;

import com.adn.inventory.dto.SupplierRequestDTO;
import com.adn.inventory.dto.SupplierResponseDTO;
import com.adn.inventory.models.Supplier;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;

import java.util.List;

public interface SupplierService {
    DatatabelOutput<Supplier> getSupplier(PagingRequest pagingRequest);

    Supplier getSupplierByid(int supplierId);

    void addSupplier(SupplierRequestDTO dto);

    void editSupplier(int id,SupplierRequestDTO dto);

    void deleteSupplier(int id);

    List<SupplierResponseDTO> getListSupplier(String keyword);

    void exportExcel();


    void insertBatch();

    void insert();


}

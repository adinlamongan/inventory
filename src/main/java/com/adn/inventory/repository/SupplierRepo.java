package com.adn.inventory.repository;

import com.adn.inventory.dto.SupplierResponseDTO;
import com.adn.inventory.models.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SupplierRepo extends CrudRepository<Supplier, Integer> {

    Page<Supplier> findAll(Pageable pageable);


    Page<Supplier> findByKodeContainingOrNamaContainingOrAlamatContainingAllIgnoreCase(String kode, String nama,
            String alamat, Pageable pageable);

    List<SupplierResponseDTO> findTop10ByKodeContainingOrNamaContainingAllIgnoreCase(String kode, String nama);
}

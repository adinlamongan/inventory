package com.adn.inventory.repository;

import com.adn.inventory.dto.CustomerResponseDTO;
import com.adn.inventory.dto.SupplierResponseDTO;
import com.adn.inventory.models.Customer;
import com.adn.inventory.models.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepo extends CrudRepository<Customer, Integer> {

    Page<Customer> findAll(Pageable pageable);


    Page<Customer> findByKodeContainingOrNamaContainingOrAlamatContainingAllIgnoreCase(String kode, String nama,
            String alamat, Pageable pageable);

    List<CustomerResponseDTO> findTop10ByKodeContainingOrNamaContainingAllIgnoreCase(String kode, String nama);
}

package com.adn.inventory.repository;

import com.adn.inventory.dto.SupplierResponseDTO;
import com.adn.inventory.models.Supplier;
import com.adn.inventory.models.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepo extends CrudRepository<Todo, Integer> {


}

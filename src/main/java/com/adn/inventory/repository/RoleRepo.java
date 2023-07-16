package com.adn.inventory.repository;

import com.adn.inventory.models.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepo extends CrudRepository<Role, Integer> {
    Page<Role> findAll(Pageable pageable);


    Page<Role> findByNameContainingAllIgnoreCase(String name, Pageable pageable);
}

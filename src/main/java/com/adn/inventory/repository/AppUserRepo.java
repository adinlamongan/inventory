package com.adn.inventory.repository;

import com.adn.inventory.models.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepo extends CrudRepository<AppUser, Integer> {

}

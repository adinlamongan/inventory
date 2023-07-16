package com.adn.inventory.repository;

import com.adn.inventory.models.AppUserQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppUserQueryRepo {
    Page<AppUserQuery> findAppUser(String keyword, String sortBy, String direction, Pageable pageable);

    Long getTotalRecord();

    AppUserQuery findAppUserByid(int id);

}

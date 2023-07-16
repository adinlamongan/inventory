package com.adn.inventory.repository.impl;

import com.adn.inventory.repository.NomorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NomorRepoImpl implements NomorRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String getNomor(String namaTabel, String nomor) {
        String query = "select MAX(CAST(right(nomor,5) AS INT)) + 1 FROM " + namaTabel +" WHERE nomor LIKE '%"+nomor+"%' ";
        try {
            return jdbcTemplate.queryForObject(query, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}

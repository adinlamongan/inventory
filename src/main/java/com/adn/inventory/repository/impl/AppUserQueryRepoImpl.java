package com.adn.inventory.repository.impl;

import com.adn.inventory.dto.POHQueryResponseDTO;
import com.adn.inventory.dto.PindahBarangHQueryResponseDTO;
import com.adn.inventory.models.AppUserQuery;
import com.adn.inventory.repository.AppUserQueryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AppUserQueryRepoImpl implements AppUserQueryRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int count(String keyword) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(mu.id) "
                + "FROM master_user mu "
                + "JOIN role r ON r.id=mu.role_id "
                + "");
        if (keyword.isEmpty()) {
            return jdbcTemplate.queryForObject(sql.toString(), Integer.class);
        } else {
            sql.append("WHERE lower(mu.name)  like lower( ? ) OR lower(r.name) like lower( ? )");
            keyword = "%" + keyword + "%";
            return jdbcTemplate.queryForObject(sql.toString(), Integer.class, keyword, keyword);
        }

    }
    @Override
    public Page<AppUserQuery> findAppUser(String keyword, String sortBy, String direction, Pageable pageable) {
        StringBuilder sql = new StringBuilder();

        sql.append(
                "SELECT  mu.id,mu.name,mu.role_id,r.name AS role_name,mu.enabled  "
                        + "FROM master_user mu "
                        + "JOIN role r ON r.id=mu.role_id  ");
        List<AppUserQuery> listData = null;
        if (keyword.isEmpty()) {

            if (sortBy == null) {
                sql.append(" ORDER BY mu.id DESC");
            } else {
                sql.append(" ORDER BY ");
                sql.append(sortBy);
                sql.append(" ");
                sql.append(direction);
            }

            sql.append(" LIMIT ");
            sql.append(pageable.getPageSize());
            sql.append(" OFFSET ");
            sql.append(pageable.getOffset());
            listData = jdbcTemplate.query(sql.toString(), new AppUserRowMapper());
        } else {
            sql.append("WHERE lower(mu.name)  like lower( ? ) ");

            if (sortBy == null) {
                sql.append(" ORDER BY mu.id DESC");
            } else {
                sql.append(" ORDER BY ");
                sql.append(sortBy);
                sql.append(" ");
                sql.append(direction);
            }

            sql.append(" LIMIT ");
            sql.append(pageable.getPageSize());
            sql.append(" OFFSET ");
            sql.append(pageable.getOffset());
            keyword = "%" + keyword + "%";
            listData = jdbcTemplate.query(sql.toString(), new AppUserRowMapper(), keyword);
        }

        return new PageImpl<>(listData, pageable, count(keyword));
    }

    @Override
    public Long getTotalRecord() {
        String queryCount = "SELECT count(mu.id) "
                + "FROM master_user mu "
                + "JOIN role r ON r.id=mu.role_id ";
        return jdbcTemplate.queryForObject(queryCount, Long.class);
    }

    @Override
    public AppUserQuery findAppUserByid(int id) {
        String sql = "SELECT mu.id,mu.name,mu.role_id,r.name AS role_name,mu.enabled "
                + "FROM master_user mu "
                + "JOIN role r ON r.id=mu.role_id "
                + "WHERE mu.id=?";

        try {
            return jdbcTemplate.queryForObject(sql,
                    new AppUserRowMapper(), id);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static class AppUserRowMapper implements RowMapper<AppUserQuery> {

        @Override
        public AppUserQuery mapRow(ResultSet rs, int rowNum) throws SQLException {
            AppUserQuery dto = new AppUserQuery();
            dto.setId(rs.getInt("id"));
            dto.setName(rs.getString("name"));
            dto.setRoleId(rs.getInt("role_id"));
            dto.setRoleName(rs.getString("role_name"));
            dto.setEnabled(rs.getBoolean("enabled"));
            return dto;
        }
    }


}

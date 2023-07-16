package com.adn.inventory.repository.impl;

import com.adn.inventory.dto.SOHQueryResponseDTO;
import com.adn.inventory.repository.SalesOrderQueryRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SalesOrderQueryRepoImpl implements SalesOrderQueryRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int count(String keyword) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(h.id) "
                + "FROM sales_orderh h "
                + "INNER JOIN master_customer c ON c.id=h.customer_id ");
        if (keyword.isEmpty()) {
            return jdbcTemplate.queryForObject(sql.toString(), Integer.class);
        } else {
            sql.append("WHERE lower(h.nomor)  like lower( ? ) OR lower(c.nama) like lower( ? )");
            keyword = "%" + keyword + "%";
            return jdbcTemplate.queryForObject(sql.toString(), Integer.class, keyword, keyword);
        }

    }
    @Override
    public Page<SOHQueryResponseDTO> findSalesOrder(String keyword, String sortBy, String direction, Pageable pageable) {
        StringBuilder sql = new StringBuilder();

        sql.append(
                "SELECT h.id,h.nomor , h.tanggal, h.customer_id, c.nama AS nama_customer, h.keterangan, h.total, h.verifikasi "
                        + "FROM sales_orderh h "
                        + "INNER JOIN master_customer c ON c.id=h.customer_id ");
        List<SOHQueryResponseDTO> listData = null;
        if (keyword.isEmpty()) {

            if (sortBy == null) {
                sql.append(" ORDER BY h.id DESC");
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
            listData = jdbcTemplate.query(sql.toString(), new SalesOrderHRowMapper());
        } else {
            sql.append("WHERE lower(h.nomor)  like lower( ? ) OR lower(c.nama) like lower( ? )");

            if (sortBy == null) {
                sql.append(" ORDER BY h.id DESC");
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
            listData = jdbcTemplate.query(sql.toString(), new SalesOrderHRowMapper(), keyword, keyword);
        }

        return new PageImpl<>(listData, pageable, count(keyword));
    }

    @Override
    public Long getTotalRecord() {
        String queryCount = ""
                + "SELECT count(h.id) "
                + "FROM sales_orderh h "
                + "INNER JOIN master_customer c ON c.id=h.customer_id ";
        return jdbcTemplate.queryForObject(queryCount, Long.class);
    }


    private static class SalesOrderHRowMapper implements RowMapper<SOHQueryResponseDTO> {

        @Override
        public SOHQueryResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            SOHQueryResponseDTO dto = new SOHQueryResponseDTO();
            dto.setId(rs.getInt("id"));
            dto.setNomor(rs.getString("nomor"));
            dto.setTanggal(rs.getDate("tanggal"));
            dto.setCustomerId(rs.getInt("customer_id"));
            dto.setNamaCustomer(rs.getString("nama_customer"));
            dto.setKeterangan(rs.getString("keterangan"));
            dto.setTotal(rs.getDouble("total"));
            dto.setVerifikasi(rs.getBoolean("verifikasi"));
            return dto;
        }
    }

}

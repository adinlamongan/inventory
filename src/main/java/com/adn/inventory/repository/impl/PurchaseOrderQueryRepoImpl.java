package com.adn.inventory.repository.impl;

import com.adn.inventory.dto.POHQueryResponseDTO;
import com.adn.inventory.repository.PurchaseOrderQueryRepo;
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
import java.util.Objects;

@Repository
public class PurchaseOrderQueryRepoImpl implements PurchaseOrderQueryRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int count(String keyword) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(h.id) "
                + "FROM purchase_orderh h "
                + "INNER JOIN master_supplier s ON s.id=h.supplier_id ");
        if (keyword.isEmpty()) {
            return jdbcTemplate.queryForObject(sql.toString(), Integer.class);
        } else {
            sql.append("WHERE lower(h.nomor)  like lower( ? ) OR lower(s.nama) like lower( ? )");
            keyword = "%" + keyword + "%";
            return jdbcTemplate.queryForObject(sql.toString(), Integer.class, keyword, keyword);
        }

    }


    @Override
    public Page<POHQueryResponseDTO> findPurchaseOrder(String keyword, String sortBy, String direction, Pageable pageable) {
        StringBuilder sql = new StringBuilder();

        sql.append(
                "SELECT h.id,h.nomor , h.tanggal, h.supplier_id, s.nama AS nama_supplier, h.keterangan, h.total, h.verifikasi "
                        + "FROM purchase_orderh h "
                        + "INNER JOIN master_supplier s ON s.id=h.supplier_id ");
        List<POHQueryResponseDTO> listData = null;
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
            listData = jdbcTemplate.query(sql.toString(), new PurchaseOrderHRowMapper());
        } else {
            sql.append("WHERE lower(h.nomor)  like lower( ? ) OR lower(s.nama) like lower( ? )");

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
            listData = jdbcTemplate.query(sql.toString(), new PurchaseOrderHRowMapper(), keyword, keyword);
        }

        return new PageImpl<>(listData, pageable, count(keyword));
    }

    @Override
    public Long getTotalRecord() {
        String queryCount = ""
                + "SELECT count(h.id) "
                + "FROM purchase_orderh h "
                + "INNER JOIN master_supplier s ON s.id=h.supplier_id ";
        return jdbcTemplate.queryForObject(queryCount, Long.class);
    }



    @Override
    public Boolean cekTerpakai(int id) {
        String query = ""
                + "SELECT sum(d.qty_terima) "
                + "FROM purchase_orderd d "
                + "WHERE d.purchase_order_id = ? ";
        try {
          double res = Objects.requireNonNull(jdbcTemplate.queryForObject(query, Number.class, id)).doubleValue();
          return res > 0 ? Boolean.TRUE : Boolean.FALSE;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static class PurchaseOrderHRowMapper implements RowMapper<POHQueryResponseDTO> {

        @Override
        public POHQueryResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            POHQueryResponseDTO dto = new POHQueryResponseDTO();
            dto.setId(rs.getInt("id"));
            dto.setNomor(rs.getString("nomor"));
            dto.setTanggal(rs.getDate("tanggal"));
            dto.setSupplierId(rs.getInt("supplier_id"));
            dto.setNamaSupplier(rs.getString("nama_supplier"));
            dto.setKeterangan(rs.getString("keterangan"));
            dto.setTotal(rs.getDouble("total"));
            dto.setVerifikasi(rs.getBoolean("verifikasi"));
            return dto;
        }
    }

}

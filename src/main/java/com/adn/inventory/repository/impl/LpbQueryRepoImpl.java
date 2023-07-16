package com.adn.inventory.repository.impl;

import com.adn.inventory.dto.LpbHQueryResponseDTO;
import com.adn.inventory.repository.LpbQueryRepo;
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
public class LpbQueryRepoImpl implements LpbQueryRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int count(String keyword) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(h.id) "
                + "FROM lpbh h "
                + "JOIN purchase_orderh poh ON poh.id=h.purchase_order_id "
                + "JOIN master_supplier ms ON ms.id=poh.supplier_id ");
        if (keyword.isEmpty()) {
            return jdbcTemplate.queryForObject(sql.toString(), Integer.class);
        } else {
            sql.append("WHERE lower(h.nomor)  like lower( ? ) ");
            sql.append("OR lower(poh.nomor) like lower( ? ) ");
            sql.append("OR lower(ms.nama) like lower( ? ) ");
            keyword = "%" + keyword + "%";
            return jdbcTemplate.queryForObject(sql.toString(), Integer.class, keyword, keyword, keyword);
        }


    }
    @Override
    public Page<LpbHQueryResponseDTO> findLpb(String keyword, String sortBy, String direction, Pageable pageable) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT h.*, poh.nomor AS nomor_po, poh.supplier_id,ms.nama AS nama_supplier "
                + "FROM lpbh h "
                + "JOIN purchase_orderh poh ON poh.id=h.purchase_order_id "
                + "JOIN master_supplier ms ON ms.id=poh.supplier_id " );
        List<LpbHQueryResponseDTO> listData = null;
        if (keyword.isEmpty()) {


            if (sortBy == null){
                sql.append(" ORDER BY h.id DESC");
            }else{
                sql.append(" ORDER BY ");
                sql.append(sortBy);
                sql.append(" ");
                sql.append(direction);
            }

            sql.append(" LIMIT ");
            sql.append(pageable.getPageSize());
            sql.append(" OFFSET ");
            sql.append(pageable.getOffset());
            listData = jdbcTemplate.query(sql.toString(), new LpbHQueryRowMapper());
        } else {
            sql.append("WHERE lower(h.nomor)  like lower( ? ) ");
            sql.append("OR lower(poh.nomor) like lower( ? ) ");
            sql.append("OR lower(ms.nama) like lower( ? ) ");

            if (sortBy == null){
                sql.append(" ORDER BY h.id DESC");
            }else{
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
            listData = jdbcTemplate.query(sql.toString(), new LpbHQueryRowMapper(),  keyword, keyword, keyword);
        }


        return new PageImpl<>(listData, pageable, count(keyword));
    }

    @Override
    public Long getTotalRecord() {
        String queryCount = ""
                + "SELECT count(h.id) "
                + "FROM lpbh h "
                + "JOIN purchase_orderh poh ON poh.id=h.purchase_order_id "
                + "JOIN master_supplier ms ON ms.id=poh.supplier_id ";
        return jdbcTemplate.queryForObject(queryCount, Long.class);
    }




    private static class LpbHQueryRowMapper implements RowMapper<LpbHQueryResponseDTO> {

        @Override
        public LpbHQueryResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            LpbHQueryResponseDTO dto = new LpbHQueryResponseDTO();
            dto.setId(rs.getInt("id"));
            dto.setNomor(rs.getString("nomor"));
            dto.setTanggal(rs.getDate("tanggal"));
            dto.setPurchaseOrderId(rs.getInt("purchase_order_id"));
            dto.setKeterangan(rs.getString("keterangan"));
            dto.setVerifikasi(rs.getBoolean("verifikasi"));
            dto.setNomorPo(rs.getString("nomor_po"));
            dto.setSupplierId(rs.getInt("supplier_id"));
            dto.setNamaSupplier(rs.getString("nama_supplier"));
            return dto;
        }
    }
}

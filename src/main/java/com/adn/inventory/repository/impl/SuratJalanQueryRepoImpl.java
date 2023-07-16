package com.adn.inventory.repository.impl;

import com.adn.inventory.dto.*;
import com.adn.inventory.repository.SuratJalanQueryRepo;
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
public class SuratJalanQueryRepoImpl implements SuratJalanQueryRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int count(String keyword) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(h.id) "
                + "FROM surat_jalanh h "
                + "JOIN sales_orderh soh ON soh.id=h.sales_order_id "
                + "JOIN master_customer mc ON mc.id=soh.customer_id ");
        if (keyword.isEmpty()) {
            return jdbcTemplate.queryForObject(sql.toString(), Integer.class);
        } else {
            sql.append("WHERE lower(h.nomor)  like lower( ? ) ");
            sql.append("OR lower(soh.nomor) like lower( ? ) ");
            sql.append("OR lower(mc.nama) like lower( ? ) ");
            keyword = "%" + keyword + "%";
            return jdbcTemplate.queryForObject(sql.toString(), Integer.class, keyword, keyword, keyword);
        }
    }

    @Override
    public Page<SuratJalanHQueryResponseDTO> findSuratJalan(String keyword, String sortBy, String direction, Pageable pageable) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT h.*, soh.nomor AS nomor_so, soh.customer_id,mc.nama AS nama_customer "
                + "FROM surat_jalanh h "
                + "JOIN sales_orderh soh ON soh.id=h.sales_order_id "
                + "JOIN master_customer mc ON mc.id=soh.customer_id " );
        List<SuratJalanHQueryResponseDTO> listData = null;
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
            listData = jdbcTemplate.query(sql.toString(), new SuratJalanHQueryRowMapper());
        } else {
            sql.append("WHERE lower(h.nomor)  like lower( ? ) ");
            sql.append("OR lower(soh.nomor) like lower( ? ) ");
            sql.append("OR lower(mc.nama) like lower( ? ) ");

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
            listData = jdbcTemplate.query(sql.toString(), new SuratJalanHQueryRowMapper(),  keyword, keyword, keyword);
        }


        return new PageImpl<>(listData, pageable, count(keyword));
    }

    @Override
    public Long getTotalRecord() {
        String queryCount = ""
                + "SELECT count(h.id) "
                + "FROM surat_jalanh h "
                + "JOIN sales_orderh soh ON soh.id=h.sales_order_id "
                + "JOIN master_customer mc ON mc.id=soh.customer_id ";
        return jdbcTemplate.queryForObject(queryCount, Long.class);
    }



    private static class SuratJalanHQueryRowMapper implements RowMapper<SuratJalanHQueryResponseDTO> {

        @Override
        public SuratJalanHQueryResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            SuratJalanHQueryResponseDTO dto = new SuratJalanHQueryResponseDTO();
            dto.setId(rs.getInt("id"));
            dto.setNomor(rs.getString("nomor"));
            dto.setTanggal(rs.getDate("tanggal"));
            dto.setSalesOrderId(rs.getInt("sales_order_id"));
            dto.setKeterangan(rs.getString("keterangan"));
            dto.setVerifikasi(rs.getBoolean("verifikasi"));
            dto.setNomorSo(rs.getString("nomor_so"));
            dto.setCustomerId(rs.getInt("customer_id"));
            dto.setNamaCustomer(rs.getString("nama_customer"));
            return dto;
        }
    }
}

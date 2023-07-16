package com.adn.inventory.repository.impl;

import com.adn.inventory.dto.*;
import com.adn.inventory.repository.JualQueryRepo;
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
public class JualQueryRepoImpl implements JualQueryRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int count(String keyword) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(h.id) "
                + "FROM jualh h "
                + "JOIN surat_jalanh sjh ON sjh.id=h.surat_jalan_id "
                + "JOIN master_customer mc ON mc.id=sjh.customer_id ");
        if (keyword.isEmpty()) {
            return jdbcTemplate.queryForObject(sql.toString(), Integer.class);
        } else {
            sql.append("WHERE lower(h.nomor)  like lower( ? ) ");
            sql.append("OR lower(sjh.nomor) like lower( ? ) ");
            sql.append("OR lower(mc.nama) like lower( ? ) ");
            keyword = "%" + keyword + "%";
            return jdbcTemplate.queryForObject(sql.toString(), Integer.class, keyword, keyword, keyword);
        }
    }
    @Override
    public Page<JualHQueryResponseDTO> findJual(String keyword, String sortBy, String direction, Pageable pageable) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT h.*, sjh.nomor AS nomor_sj, soh.nomor AS nomor_so, sjh.customer_id,mc.nama AS nama_customer "
                + "FROM jualh h "
                + "JOIN surat_jalanh sjh ON sjh.id=h.surat_jalan_id "
                + "JOIN sales_orderh soh ON soh.id=sjh.sales_order_id "
                + "JOIN master_customer mc ON mc.id=sjh.customer_id " );
        List<JualHQueryResponseDTO> listData = null;
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
            listData = jdbcTemplate.query(sql.toString(), new JualHQueryRowMapper());
        } else {
            sql.append("WHERE lower(h.nomor)  like lower( ? ) ");
            sql.append("OR lower(sjh.nomor) like lower( ? ) ");
            sql.append("OR lower(mc.nama) like lower( ? ) ");

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
            listData = jdbcTemplate.query(sql.toString(), new JualHQueryRowMapper(), keyword, keyword, keyword);
        }
        return new PageImpl<>(listData, pageable, count(keyword));
    }

    @Override
    public Long getTotalRecord() {
        String queryCount = ""
                + "SELECT count(h.id) "
                + "FROM jualh h "
                + "JOIN surat_jalanh sjh ON sjh.id=h.surat_jalan_id "
                + "JOIN master_customer mc ON mc.id=sjh.customer_id ";
        return jdbcTemplate.queryForObject(queryCount, Long.class);
    }

    private static class JualHQueryRowMapper implements RowMapper<JualHQueryResponseDTO> {

        @Override
        public JualHQueryResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            JualHQueryResponseDTO dto = new JualHQueryResponseDTO();
            dto.setId(rs.getInt("id"));
            dto.setNomor(rs.getString("nomor"));
            dto.setTanggal(rs.getDate("tanggal"));
            dto.setSuratJalanId(rs.getInt("surat_jalan_id"));
            dto.setKeterangan(rs.getString("keterangan"));
            dto.setSubTotal(rs.getDouble("sub_total"));
            dto.setPpn(rs.getDouble("ppn"));
            dto.setPpnNominal(rs.getDouble("ppn_nominal"));
            dto.setDiskon(rs.getDouble("diskon"));
            dto.setTotal(rs.getDouble("total"));
            dto.setVerifikasi(rs.getBoolean("verifikasi"));
            dto.setLunas(rs.getBoolean("lunas"));
            dto.setNomorSj(rs.getString("nomor_sj"));
            dto.setNomorSo(rs.getString("nomor_so"));
            dto.setCustomerId(rs.getInt("customer_id"));
            dto.setNamaCustomer(rs.getString("nama_customer"));
            return dto;
        }
    }
}

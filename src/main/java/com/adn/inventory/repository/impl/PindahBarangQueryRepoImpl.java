package com.adn.inventory.repository.impl;

import com.adn.inventory.dto.PindahBarangHQueryResponseDTO;
import com.adn.inventory.repository.PindahBarangQueryRepo;
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
public class PindahBarangQueryRepoImpl implements PindahBarangQueryRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public int count(String keyword) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(h.id) "
                + "FROM pindah_barangh h "
                + "INNER JOIN master_gudang asal ON asal.id=h.gudang_asal_id "
                + "INNER JOIN master_gudang tujuan ON tujuan.id=h.gudang_tujuan_id ");
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
    public Page<PindahBarangHQueryResponseDTO> findPindahBarang(String keyword, String sortBy, String direction, Pageable pageable) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT h.*, asal.nama AS gudang_asal, tujuan.nama AS gudang_tujuan  "
                + "FROM pindah_barangh h  "
                + "INNER JOIN master_gudang asal ON asal.id=h.gudang_asal_id  "
                + "INNER JOIN master_gudang tujuan ON tujuan.id=h.gudang_tujuan_id ");
        List<PindahBarangHQueryResponseDTO> listData = null;
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
            listData = jdbcTemplate.query(sql.toString(), new PindahBarangHQueryRowMapper());
        } else {
            sql.append("WHERE lower(h.nomor)  like lower( ? ) ");
            sql.append("OR lower(asal.nama) like lower( ? ) ");
            sql.append("OR lower(tujuan.nama) like lower( ? ) ");

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
            listData = jdbcTemplate.query(sql.toString(), new PindahBarangHQueryRowMapper(), keyword, keyword, keyword);
        }
        return new PageImpl<>(listData, pageable, count(keyword));

    }

    @Override
    public Long getTotalRecord() {
        String queryCount = ""
                + "SELECT count(h.id) "
                + "FROM pindah_barangh h "
                + "INNER JOIN master_gudang asal ON asal.id=h.gudang_asal_id "
                + "INNER JOIN master_gudang tujuan ON tujuan.id=h.gudang_tujuan_id ";
        return jdbcTemplate.queryForObject(queryCount, Long.class);
    }

    private static class PindahBarangHQueryRowMapper implements RowMapper<PindahBarangHQueryResponseDTO> {

        @Override
        public PindahBarangHQueryResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            PindahBarangHQueryResponseDTO dto = new PindahBarangHQueryResponseDTO();
            dto.setId(rs.getInt("id"));
            dto.setNomor(rs.getString("nomor"));
            dto.setTanggal(rs.getDate("tanggal"));
            dto.setGudangAsalId(rs.getInt("gudang_asal_id"));
            dto.setGudangAsal(rs.getString("gudang_asal"));
            dto.setGudangTujuanId(rs.getInt("gudang_tujuan_id"));
            dto.setGudangTujuan(rs.getString("gudang_tujuan"));
            dto.setKeterangan(rs.getString("keterangan"));
            dto.setVerifikasi(rs.getBoolean("verifikasi"));
            return dto;
        }
    }

}





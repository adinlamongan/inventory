package com.adn.inventory.repository.impl;

import com.adn.inventory.dto.UmurPiutangQueryResponseDTO;
import com.adn.inventory.repository.UmurPiutangRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UmurPiutangRepoImpl implements UmurPiutangRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<UmurPiutangQueryResponseDTO> getData(int customerId) {
        String sqlCustomer = "";
        if (customerId > 0) {
            sqlCustomer = " AND h.customer_id=? ";
        }
        String sql = "SELECT customer_id, "
                    + "     nama_customer, "
                    + "     COALESCE(ar_30, 0, ar_30) AS ar_30, "
                    + "     COALESCE(ar_31_60, 0, ar_31_60) AS ar_31_60, "
                    + "     COALESCE(ar_61_90, 0, ar_61_90) AS ar_61_90, "
                    + "     COALESCE(ar_90_lebih, 0, ar_90_lebih) AS ar_90_lebih "
                    + "FROM ( "
                    + "         SELECT h.customer_id,mc.nama AS nama_customer,  "
                    + "             SUM(h.total - h.bayar) FILTER (WHERE CURRENT_DATE - h.tanggal  < 31) AS ar_30, "
                    + "             SUM(h.total - h.bayar) FILTER (WHERE CURRENT_DATE - h.tanggal > 30 and CURRENT_DATE - h.tanggal  < 61) AS ar_31_60, "
                    + "             SUM(h.total - h.bayar) FILTER (WHERE CURRENT_DATE - h.tanggal > 60 and CURRENT_DATE - h.tanggal  < 91) AS ar_61_90, "
                    + "             SUM(h.total - h.bayar) FILTER (WHERE CURRENT_DATE - h.tanggal > 91 ) AS ar_90_lebih "
                    + "         FROM jualh h "
                    + "         JOIN master_customer mc ON mc.id=h.customer_id "
                    + "         WHERE h.lunas=FALSE " + sqlCustomer
                    + "         GROUP BY h.customer_id,mc.nama "
                    + "     ) AS v";

        try {
            if (customerId == 0) {
                return jdbcTemplate.query(sql, new UmurPiutangQueryRowMapper());
            }
            return jdbcTemplate.query(sql, new UmurPiutangQueryRowMapper(),customerId);


        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static class UmurPiutangQueryRowMapper implements RowMapper<UmurPiutangQueryResponseDTO> {

        @Override
        public UmurPiutangQueryResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            UmurPiutangQueryResponseDTO dto = new UmurPiutangQueryResponseDTO();
            dto.setCustomer_id(rs.getInt("customer_id"));
            dto.setNama_customer(rs.getString("nama_customer"));
            dto.setAr_30(rs.getDouble("ar_30"));
            dto.setAr_31_60(rs.getDouble("ar_31_60"));
            dto.setAr_61_90(rs.getDouble("ar_61_90"));
            dto.setAr_90_lebih(rs.getDouble("ar_90_lebih"));
            return dto;
        }
    }
}

package com.adn.inventory.repository.impl;

import com.adn.inventory.dto.ProdukResponseDTO;
import com.adn.inventory.repository.BarangQueryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BarangQueryRepoImpl implements BarangQueryRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ProdukResponseDTO> listBarangAutoComplete(String keyword) {
        String sql = "SELECT p.id,p.kode,p.nama,p.kategori_id , k.nama AS nama_kategori, "
                + "p.satuan_id, s.nama AS nama_satuan,p.qty "
                + "FROM master_produk p "
                + "INNER JOIN master_kategori k ON p.kategori_id=k.id "
                + "INNER JOIN master_satuan s ON p.satuan_id=s.id "
                + "WHERE lower(p.kode)  like lower( ?  ) OR lower(p.nama) like lower( ? ) LIMIT 10";
        keyword = "%" + keyword + "%";
        return jdbcTemplate.query(sql,
                (rs, rowNum) ->
                        new ProdukResponseDTO(
                                rs.getInt("id"),
                                rs.getString("kode"),
                                rs.getString("nama"),
                                rs.getInt("kategori_id"),
                                rs.getString("nama_kategori"),
                                rs.getInt("satuan_id"),
                                rs.getString("nama_satuan"),
                                rs.getBigDecimal("qty")
                        ), keyword, keyword);
    }
}

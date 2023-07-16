package com.adn.inventory.repository;

import com.adn.inventory.dto.ProdukResponseDTO;
import com.adn.inventory.models.Produk;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BarangRepo extends CrudRepository<Produk, Integer> {

    @Query("SELECT p.id,p.kode,p.nama,p.kategori_id , k.nama AS nama_kategori, "
            + "p.satuan_id, s.nama AS nama_satuan,p.qty "
            + "FROM master_produk p "
            + "INNER JOIN master_kategori k ON p.kategori_id=k.id "
            + "INNER JOIN master_satuan s ON p.satuan_id=s.id "
            + "WHERE lower(p.kode)  like lower(:keyword) OR lower(p.nama) like lower(:keyword) LIMIT 10")
    List<ProdukResponseDTO> listBarangAutoComplete(@Param("keyword") String keyword);

}

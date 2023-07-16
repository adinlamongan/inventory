package com.adn.inventory.repository;

import com.adn.inventory.models.StokGudang;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface StokGudangRepo extends CrudRepository<StokGudang, Integer> {

    @Query("select * from stok_gudang where produk_id=:produkId AND gudang_id=:gudangId for update")
    StokGudang findAndLockByProodukIdAndGudangId(@Param("produkId") int produkId, @Param("gudangId") int gudangId);

    @Query("select * from stok_gudang where id=:id for update")
    Optional<StokGudang> findAndLockById(@Param("id") int id);

    StokGudang findByProdukIdAndGudangId(int produkId, int gudangId);

    @Query("SELECT sg.* FROM stok_gudang sg"
            + "JOIN surat_jaland sjd ON sg.id=sjd.stok_id "
            + "where sjd.surat_jalan_id=:suratJalanId for update")
    void findAndLockBySuratJalanId(int suratJalanId);



}

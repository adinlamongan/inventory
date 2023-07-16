package com.adn.inventory.repository;

import com.adn.inventory.dto.PindahBarangAutocompleteBarangByGudangAsalResponseDTO;
import com.adn.inventory.dto.PindahBarangDQueryResponseDTO;
import com.adn.inventory.models.PindahBarangD;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PindahBarangDRepo extends CrudRepository<PindahBarangD, Integer> {

    @Modifying
    @Query("DELETE FROM pindah_barangd where pindah_barang_id=:pindahBarangId")
    void deleteByPindahBarangId(@Param("pindahBarangId") int pindahBarangId);

    List<PindahBarangD> findAllByPindahBarangId(int pindahBarangId);

    @Query("SELECT d.*,mp.kode AS kode_produk,mp.nama AS nama_produk, ms.nama as nama_satuan "
            + "FROM pindah_barangd d "
            + "JOIN master_produk mp ON mp.id=d.produk_id "
            + "INNER JOIN master_satuan ms ON ms.id=mp.satuan_id "
            + "WHERE d.pindah_barang_id=:pindahBarangId ORDER BY d.id")
    List<PindahBarangDQueryResponseDTO> findDetailByPindahBarangId(@Param("pindahBarangId") int pindahBarangId);


    @Query("SELECT s.*,p.kode, p.nama , ms.nama AS nama_satuan, mk.nama as nama_kategori,  "
            + "s.qty AS sisa "
            + "FROM stok_gudang s "
            + "INNER JOIN master_produk p ON p.id=s.produk_id "
            + "INNER JOIN master_satuan ms ON ms.id=p.satuan_id "
            + "INNER JOIN master_kategori mk ON mk.id=p.kategori_id "
            + "WHERE s.gudang_id =:gudangAsalId "
            + "AND ( lower(p.kode) like lower( :keyword ) OR lower(p.nama) like lower( :keyword ) ) "
            + "AND s.qty > 0"
            + "LIMIT 10")
    List<PindahBarangAutocompleteBarangByGudangAsalResponseDTO> findStokByKeywordAndGudangId(@Param("gudangAsalId") int gudangAsalId, @Param("keyword") String keyword );

    @Query("SELECT s.*,p.kode, p.nama , ms.nama AS nama_satuan, mk.nama as nama_kategori,  "
            + "(s.qty  - COALESCE((SELECT SUM(qty) FROM pindah_barangd WHERE stok_id=s.id AND pindah_barang_id!= ? ),0)) AS sisa "
            + "FROM stok_gudang s "
            + "INNER JOIN master_produk p ON p.id=s.produk_id "
            + "INNER JOIN master_satuan ms ON ms.id=p.satuan_id "
            + "INNER JOIN master_kategori mk ON mk.id=p.kategori_id "
            + "WHERE s.gudang_id =:gudangAsalId "
            + "AND ( lower(p.kode) like lower( :keyword ) OR lower(p.nama) like lower( :keyword ) ) "
            + "AND (s.qty  - COALESCE((SELECT SUM(qty) FROM pindah_barangd WHERE stok_id=s.id AND pindah_barang_id!=:pindahBarangId ),0)) > 0"
            + "LIMIT 10")
    List<PindahBarangAutocompleteBarangByGudangAsalResponseDTO> findStokByKeywordAndGudangIdForEdit(@Param("gudangAsalId") int gudangAsalId, @Param("keyword") String keyword,@Param("pindahBarangId") int pindahBarangId );


}

package com.adn.inventory.repository;

import com.adn.inventory.dto.JualDQueryResponseDTO;
import com.adn.inventory.dto.JualListProdukSJResponseDTO;
import com.adn.inventory.models.JualD;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JualDRepo extends CrudRepository<JualD, Integer> {

    @Modifying
    @Query("DELETE FROM juald where jual_id=:jualId")
    void deleteByJualId(@Param("jualId") int jualId);

    List<JualD> findAllByJualId(int jualId);

    @Query( "SELECT d.*,mp.kode AS kode_produk,mp.nama AS nama_produk, ms.nama as nama_satuan "
            + "FROM juald d "
            + "JOIN master_produk mp ON mp.id=d.produk_id "
            + "JOIN master_satuan ms ON ms.id=mp.satuan_id "
            + "WHERE d.jual_id=:jualId ORDER BY d.id")
    List<JualDQueryResponseDTO> findDetailByJualId(@Param("jualId") int jualId);

    @Query("SELECT d.*,sod.harga,mp.kode AS kode_produk, mp.nama AS nama_produk, ms.nama AS nama_satuan "
            + "FROM surat_jaland d "
            + "JOIN sales_orderd sod ON sod.id=d.sales_order_detail_id "
            + "JOIN master_produk mp ON mp.id=d.produk_id "
            + "JOIN master_satuan ms ON ms.id=mp.satuan_id "
            + "WHERE  d.surat_jalan_id=:suratJalanId ORDER BY d.id")
    List<JualListProdukSJResponseDTO> findSuratJalanDBySuratJalanId(@Param("suratJalanId") int suratJalanId);


}

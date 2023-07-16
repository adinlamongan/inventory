package com.adn.inventory.repository;

import com.adn.inventory.dto.SODQueryResponseDTO;
import com.adn.inventory.dto.SuratJalanDQueryResponseDTO;
import com.adn.inventory.models.SuratJalanD;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SuratJalanDRepo extends CrudRepository<SuratJalanD, Integer> {

    @Modifying
    @Query("DELETE FROM surat_jaland where surat_jalan_id=:suratJalanId")
    void deleteBySuratJalanId(@Param("suratJalanId") int suratJalanId);

    List<SuratJalanD> findAllBySuratJalanId(int suratJalanId);

    @Query("SELECT d.*,mp.kode AS kode_produk,mp.nama AS nama_produk,mg.nama AS nama_gudang, ms.nama as nama_satuan, sod.qty as qty_so, "
            + " sg.qty as qty_stok "
            + "FROM surat_jaland d "
            + "JOIN master_produk mp ON mp.id=d.produk_id "
            + "JOIN master_gudang mg ON mg.id=d.gudang_id "
            + "JOIN master_satuan ms ON ms.id=mp.satuan_id "
            + "JOIN stok_gudang sg ON sg.id=d.stok_id "
            + "JOIN sales_orderd sod ON sod.id=d.sales_order_detail_id "
            + "WHERE d.surat_jalan_id=:suratJalanId ORDER BY d.id")
    List<SuratJalanDQueryResponseDTO> findDetailBySuratJalanId(@Param("suratJalanId") int suratJalanId);


    @Query("SELECT d.id,d.sales_order_id, d.produk_id , mp.kode as kode_produk, mp.nama AS nama_produk, ms.nama as nama_satuan, d.harga,d.qty,d.total "
            + "FROM sales_orderd d "
            + "INNER JOIN master_produk mp ON mp.id=d.produk_id "
            + "INNER JOIN master_satuan ms ON ms.id=mp.satuan_id "
            + "WHERE d.sales_order_id=:salesOrderId "
            + "ORDER BY d.id")
    List<SODQueryResponseDTO> findSalesOrderDBySalesOrderId(@Param("salesOrderId") int salesOrderId);



}

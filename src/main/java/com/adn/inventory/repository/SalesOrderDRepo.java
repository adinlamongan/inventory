package com.adn.inventory.repository;

import com.adn.inventory.dto.SODQueryResponseDTO;
import com.adn.inventory.models.SalesOrderD;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalesOrderDRepo extends CrudRepository<SalesOrderD, Integer> {
    @Modifying
    @Query("DELETE FROM sales_orderd where sales_order_id=:soId")
    void deleteBySalesOrderId(@Param("soId") int poId);

    @Query("SELECT d.id,d.sales_order_id, d.produk_id , mp.kode as kode_produk, mp.nama AS nama_produk,d.harga,d.qty,d.total, ms.nama as nama_satuan "
            + "FROM sales_orderd d "
            + "INNER JOIN master_produk mp ON mp.id=d.produk_id "
            + "INNER JOIN master_satuan ms ON ms.id=mp.satuan_id "
            + "WHERE d.sales_order_id=:salesOrderId ORDER BY d.id")
    List<SODQueryResponseDTO> findDetailDBySalesOrderId(@Param("salesOrderId") int salesOrderId);



}

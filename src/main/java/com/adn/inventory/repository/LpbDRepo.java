package com.adn.inventory.repository;

import com.adn.inventory.dto.LpbDQueryResponseDTO;
import com.adn.inventory.dto.PODQueryResponseDTO;
import com.adn.inventory.models.LpbD;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LpbDRepo extends CrudRepository<LpbD, Integer> {

    @Modifying
    @Query("DELETE FROM lpbd where lpb_id=:lpbId")
    void deleteByLpbId(@Param("lpbId") int lpbId);

    @Query("SELECT purchase_order_detail_id, qty FROM lpbd where lpb_id=:lpbId")
    List<LpbD> findByLpbId(@Param("lpbId") int lpbId);

    List<LpbD> findAllByLpbId(int lpbId);

    @Query("SELECT d.*,mp.kode AS kode_produk,mp.nama AS nama_produk, ms.nama as nama_satuan, mg.nama AS nama_gudang, pod.qty as qty_po "
            + "FROM lpbd d "
            + "JOIN master_produk mp ON mp.id=d.produk_id "
            + "JOIN master_satuan ms ON ms.id=mp.satuan_id "
            + "JOIN master_gudang mg ON mg.id=d.gudang_id "
            + "JOIN purchase_orderd pod ON pod.id=d.purchase_order_detail_id "
            + "WHERE d.lpb_id=:lpbId ORDER BY d.id")
    List<LpbDQueryResponseDTO> findDetailByLpbId(@Param("lpbId") int lpbId);

    @Query("SELECT d.id,d.purchase_order_id, d.produk_id , mp.kode as kode_produk, mp.nama AS nama_produk, ms.nama as nama_satuan, d.harga,(d.qty - qty_terima) as qty,d.total "
            + "FROM purchase_orderd d "
            + "INNER JOIN master_produk mp ON mp.id=d.produk_id "
            + "INNER JOIN master_satuan ms ON ms.id=mp.satuan_id "
            + "WHERE d.purchase_order_id=:purchaseOrderId "
            + "AND (d.qty-d.qty_terima) > 0"
            + "ORDER BY d.id")
    List<PODQueryResponseDTO> findPurchaseOrderDByPurchaseOrderId(@Param("purchaseOrderId") int purchaseOrderId);

}

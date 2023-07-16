package com.adn.inventory.repository;

import com.adn.inventory.dto.PODQueryResponseDTO;
import com.adn.inventory.models.PurchaseOrderD;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderDRepo extends CrudRepository<PurchaseOrderD, Integer> {

    @Modifying
    @Query("DELETE FROM purchase_orderd where purchase_order_id=:poId")
    void deleteByPurchaseOrderId(@Param("poId") int poId);

    @Query("select * from purchase_orderd where id=:id for update")
    Optional<PurchaseOrderD> findAndLockById(@Param("id") int id);

    @Lock(LockMode.PESSIMISTIC_WRITE)
    Optional<PurchaseOrderD> findById(int id);


    @Query("SELECT d.id,d.purchase_order_id, d.produk_id , mp.kode as kode_produk, mp.nama AS nama_produk, ms.nama as nama_satuan,d.harga,d.qty,d.total "
            + "FROM purchase_orderd d "
            + "INNER JOIN master_produk mp ON mp.id=d.produk_id "
            + "INNER JOIN master_satuan ms ON ms.id=mp.satuan_id "
            + "WHERE d.purchase_order_id=:purchaseOrderId ORDER BY d.id")
    List<PODQueryResponseDTO> findDetailByPurchaseOrderId(@Param("purchaseOrderId") int purchaseOrderId);
}

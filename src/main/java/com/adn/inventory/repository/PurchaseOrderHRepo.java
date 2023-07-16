package com.adn.inventory.repository;

import com.adn.inventory.dto.POHQueryResponseDTO;
import com.adn.inventory.models.PurchaseOrderH;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PurchaseOrderHRepo extends CrudRepository<PurchaseOrderH, Integer> {


    @Query("SELECT h.id,h.nomor,  h.tanggal, h.supplier_id, s.nama AS nama_supplier, h.keterangan,h.total,h.verifikasi "
            + "FROM purchase_orderh h "
            + "INNER JOIN master_supplier s ON s.id=h.supplier_id "
            + "WHERE h.id=:id")
    POHQueryResponseDTO findHeaderById(int id);

    @Modifying
    @Query("UPDATE purchase_orderh "
            + "SET habis=(SELECT CASE "
            + "             WHEN  "
            + "             SUM(qty-qty_terima) = 0 THEN true "
            + "             ELSE false "
            + "             END "
            + " FROM purchase_orderd d "
            + "WHERE d.purchase_order_id=purchase_orderh.id) "
            + "WHERE id=:purchaseOrderId")
    int updateHabisPO(@Param("purchaseOrderId") int purchaseOrderId);

}

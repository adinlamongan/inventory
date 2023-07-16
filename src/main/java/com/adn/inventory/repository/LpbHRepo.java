package com.adn.inventory.repository;

import com.adn.inventory.dto.LpbHQueryResponseDTO;
import com.adn.inventory.dto.LpbListPOHQueryResponseDTO;
import com.adn.inventory.models.LpbH;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LpbHRepo extends CrudRepository<LpbH, Integer> {
    @Query("SELECT h.*, poh.nomor AS nomor_po, poh.supplier_id,ms.nama AS nama_supplier "
            + "FROM lpbh h "
            + "JOIN purchase_orderh poh ON poh.id=h.purchase_order_id "
            + "JOIN master_supplier ms ON ms.id=poh.supplier_id "
            + "WHERE h.id=:id")
    LpbHQueryResponseDTO findHeaderHById(@Param("id") int id);

    @Query("SELECT h.id,h.nomor , h.tanggal, h.supplier_id, s.nama AS nama_supplier, h.keterangan, h.total, h.verifikasi "
            + " FROM purchase_orderh h "
            + " INNER JOIN master_supplier s ON s.id=h.supplier_id "
            + " WHERE ( lower(h.nomor)  like lower( :keyword ) OR lower(s.nama) like lower( :keyword ) ) "
            + " AND h.verifikasi = true "
            + " AND h.habis = false "
            + " LIMIT 10 ")
    List<LpbListPOHQueryResponseDTO> findPurchaseOrderByLike(@Param("keyword") String keyword);


}

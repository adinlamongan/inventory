package com.adn.inventory.repository;

import com.adn.inventory.dto.SOHQueryResponseDTO;
import com.adn.inventory.models.SalesOrderH;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SalesOrderHRepo extends CrudRepository<SalesOrderH, Integer> {

    @Query("SELECT h.id,h.nomor,  h.tanggal, h.customer_id, c.nama AS nama_customer, h.keterangan,h.total,h.verifikasi "
            + "FROM sales_orderh h "
            + "INNER JOIN master_customer c ON c.id=h.customer_id "
            + "WHERE h.id=:id")
    SOHQueryResponseDTO findHeaderById(@Param("id") int id);

}

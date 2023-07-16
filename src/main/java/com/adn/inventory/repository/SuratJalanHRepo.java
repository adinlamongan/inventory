package com.adn.inventory.repository;

import com.adn.inventory.dto.SuratJalanHQueryResponseDTO;
import com.adn.inventory.dto.SuratJalanListSOResponseDTO;
import com.adn.inventory.models.LpbH;
import com.adn.inventory.models.SuratJalanH;
import org.apache.xpath.operations.Bool;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SuratJalanHRepo extends CrudRepository<SuratJalanH, Integer> {

    Boolean existsBySalesOrderId(int salesOrderId);

    @Query("SELECT h.*, soh.nomor AS nomor_so, soh.customer_id,mc.nama AS nama_customer "
            + "FROM surat_jalanh h "
            + "JOIN sales_orderh soh ON soh.id=h.sales_order_id "
            + "JOIN master_customer mc ON mc.id=soh.customer_id "
            + "WHERE h.id=:id")
    SuratJalanHQueryResponseDTO findHeaderById(@Param("id") int id);

    @Query("SELECT h.id,h.nomor , h.tanggal, h.customer_id, c.nama AS nama_customer, h.keterangan, h.total, h.verifikasi "
            + " FROM sales_orderh h "
            + " INNER JOIN master_customer c ON c.id=h.customer_id "
            + " WHERE ( lower(h.nomor)  like lower( :keyword ) OR lower(c.nama) like lower( :keyword ) ) "
            + " AND h.verifikasi = true "
            + " AND h.terpakai = false "
            + " LIMIT 10 ")
    List<SuratJalanListSOResponseDTO> findSalesOrderByLike(@Param("keyword") String keyword);


}

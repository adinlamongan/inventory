package com.adn.inventory.repository;

import com.adn.inventory.dto.JualHQueryResponseDTO;
import com.adn.inventory.dto.JualListSJQueryResponseDTO;
import com.adn.inventory.models.JualH;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JualHRepo extends CrudRepository<JualH, Integer> {

    @Lock(LockMode.PESSIMISTIC_WRITE)
    Optional<JualH> findById(int id);

    @Query("SELECT h.*, sjh.nomor AS nomor_sj, soh.nomor AS nomor_so, sjh.customer_id,mc.nama AS nama_customer "
            + "FROM jualh h "
            + "JOIN surat_jalanh sjh ON sjh.id=h.surat_jalan_id "
            + "JOIN sales_orderh soh ON soh.id=sjh.sales_order_id "
            + "JOIN master_customer mc ON mc.id=sjh.customer_id "
            + "WHERE h.id=:id")
    JualHQueryResponseDTO findHeaderById(@Param("id") int id);

    @Query("SELECT h.id,h.nomor , h.tanggal, h.customer_id, c.nama AS nama_customer, soh.nomor as nomor_so "
            + " FROM surat_jalanh h "
            + " INNER JOIN sales_orderh soh ON soh.id=h.sales_order_id"
            + " INNER JOIN master_customer c ON c.id=h.customer_id "
            + " WHERE ( lower(h.nomor)  like lower( :keyword ) OR lower(c.nama) like lower( :keyword ) ) "
            + " AND h.verifikasi = true "
            + " AND h.terpakai = false "
            + " LIMIT 10 ")
    List<JualListSJQueryResponseDTO> findSuratJalanByLike(@Param("keyword") String keyword);

}

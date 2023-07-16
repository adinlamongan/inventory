package com.adn.inventory.repository;

import com.adn.inventory.dto.BayarDQueryResponseDTO;
import com.adn.inventory.dto.BayarListInvoiceQueryResponseDTO;
import com.adn.inventory.models.BayarD;
import com.adn.inventory.models.JualD;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BayarDRepo extends CrudRepository<BayarD, Integer> {

    @Modifying
    @Query("DELETE FROM bayard where bayar_id=:bayarId")
    void deleteByBayarId(@Param("bayarId") int bayarId);

    List<BayarD> findAllByJualId(int bayarId);

    @Query("SELECT d.*,jh.nomor as nomor_invoice, jh.tanggal, jh.total as nilai_invoice, "
            + " jh.total - (SELECT COALESCE(SUM(jml_bayar), 0,SUM(jml_bayar)) FROM bayard WHERE jual_id=jh.id AND bayar_id!=:bayarId)  as jml_tagihan  "
            + "FROM bayard d "
            + "JOIN jualh jh ON jh.id=d.jual_id "
            + "WHERE d.bayar_id=:bayarId ORDER BY d.id")
    List<BayarDQueryResponseDTO> findDetatailByBayarId(@Param("bayarId") int bayarId);

    @Query("SELECT jh.id,jh.nomor , jh.tanggal, jh.total as nilai_invoice, jh.total - jh.bayar - COALESCE (v.jml_bayar, 0 , v.jml_bayar)   as sisa_tagihan "
            + " FROM jualh jh "
            + " LEFT JOIN ("
            + "         SELECT h.customer_id,d.jual_id,SUM(d.jml_bayar) AS jml_bayar FROM bayard d "
            + "         JOIN bayarh h ON h.id=d.bayar_id "
            + "         WHERE h.verifikasi=FALSE "
            + "             AND h.customer_id =:customerId"
            + "             GROUP BY h.customer_id, d.jual_id"
            + "         ) "
            + " AS v ON v.jual_id=jh.id"
            + " WHERE jh.verifikasi=true AND jh.lunas=false "
            + " AND jh.total - jh.bayar - COALESCE (v.jml_bayar, 0 , v.jml_bayar) > 0 "
            + " AND jh.customer_id=:customerId " )
    List<BayarListInvoiceQueryResponseDTO> findJualHByCustomerId(@Param("customerId") int customerId);

    @Query("SELECT jh.id,jh.nomor , jh.tanggal, jh.total as nilai_invoice, jh.total - jh.bayar - COALESCE (v.jml_bayar, 0 , v.jml_bayar)   as sisa_tagihan "
            + " FROM jualh jh "
            + " LEFT JOIN ("
            + "         SELECT h.customer_id,d.jual_id,SUM(d.jml_bayar) AS jml_bayar FROM bayard d "
            + "         JOIN bayarh h ON h.id=d.bayar_id "
            + "         WHERE h.verifikasi=FALSE "
            + "             AND h.customer_id =:customerId AND h.id!=:bayarId"
            + "             GROUP BY h.customer_id, d.jual_id"
            + "         ) "
            + " AS v ON v.jual_id=jh.id"
            + " WHERE jh.verifikasi=true AND jh.lunas=false "
            + " AND jh.total - jh.bayar - COALESCE (v.jml_bayar, 0 , v.jml_bayar) > 0 "
            + " AND jh.customer_id=:customerId " )
    List<BayarListInvoiceQueryResponseDTO> findJualHByCustomerIdForEdit(@Param("customerId") int customerId, @Param("bayarId") int bayarId);

}

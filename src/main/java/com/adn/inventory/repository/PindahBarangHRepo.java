package com.adn.inventory.repository;

import com.adn.inventory.dto.PindahBarangHQueryResponseDTO;
import com.adn.inventory.models.PindahBarangH;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface PindahBarangHRepo extends CrudRepository<PindahBarangH, Integer> {

    @Query("SELECT h.*, asal.nama AS gudang_asal, tujuan.nama AS gudang_tujuan "
            + "FROM pindah_barangh h "
            + "INNER JOIN master_gudang asal ON asal.id=h.gudang_asal_id "
            + "INNER JOIN master_gudang tujuan ON tujuan.id=h.gudang_tujuan_id "
            + "WHERE h.id=:id")
    PindahBarangHQueryResponseDTO findHeaderById(int id);

}

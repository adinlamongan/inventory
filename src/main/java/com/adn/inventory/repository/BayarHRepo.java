package com.adn.inventory.repository;

import com.adn.inventory.dto.BayarHQueryResponseDTO;
import com.adn.inventory.models.BayarH;
import com.adn.inventory.models.JualH;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BayarHRepo extends CrudRepository<BayarH, Integer> {

    @Query( "SELECT h.*, mc.nama AS nama_customer "
            + "FROM bayarh h "
            + "JOIN master_customer mc ON mc.id=h.customer_id "
            + "WHERE h.id=:id")
    BayarHQueryResponseDTO findHeaderById(@Param("id") int id);
}

package com.adn.inventory.repository;

import com.adn.inventory.models.Stok;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StokRepo extends CrudRepository<Stok, Integer> {

    @Query("SELECT SUM(terpakai) as terpakai FROM stok where reference_id=:referenceId AND table_name=:tableName ")
    Stok getStokByReferenceIdAndTableName(int referenceId, String tableName);

    @Modifying
    @Query("DELETE FROM stok where reference_id=:referenceId AND table_name=:tableName ")
    void deleteStokByReferenceIdAndTableName(int referenceId, String tableName);

    @Query("select id, qty, terpakai from stok where id=:id")
    Optional<Stok> findStok(@Param("id") int id);


}

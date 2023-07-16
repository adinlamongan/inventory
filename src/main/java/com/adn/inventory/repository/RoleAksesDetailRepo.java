package com.adn.inventory.repository;

import com.adn.inventory.models.RoleAksesDetail;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RoleAksesDetailRepo extends CrudRepository<RoleAksesDetail, Integer> {

    @Modifying
    @Query("DELETE FROM role_akses_detail where role_id=:roleId")
    void deleteByRoleId(@Param("roleId") int roleId);
}

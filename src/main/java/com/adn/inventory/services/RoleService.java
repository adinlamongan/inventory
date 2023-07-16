package com.adn.inventory.services;

import com.adn.inventory.dto.GroupMenuResponseDTO;
import com.adn.inventory.dto.RoleRequestDTO;
import com.adn.inventory.dto.RoleSettingRequestDTO;
import com.adn.inventory.models.Role;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;

import java.util.List;

public interface RoleService {
    DatatabelOutput<Role> getRoles(PagingRequest pagingRequest);

    Role getRoleByid(int id);

    void editRole(RoleRequestDTO dto);

    void addRole(RoleRequestDTO dto);

    void deleteRole(RoleRequestDTO dto);

    void settingSaveRole(RoleSettingRequestDTO dto);

    List<GroupMenuResponseDTO> getMenuSub(int id);


}

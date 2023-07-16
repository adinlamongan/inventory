package com.adn.inventory.repository;

import com.adn.inventory.dto.MenuSubResponseDTO;

import java.util.List;

public interface MenuSubQueryRepo {
    List<MenuSubResponseDTO> getData(int roleId);

    void insertIntoRoleAksesDetail(int roleId);
}

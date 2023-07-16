package com.adn.inventory.repository;

import com.adn.inventory.dto.MenuSubResponseDTO;
import com.adn.inventory.models.UserInfo;

import java.util.List;
import java.util.Optional;

public interface UserInfoRepo {

    Optional<UserInfo> getUserInfo(String username);

    List<MenuSubResponseDTO> getAksesRole(int userId);
}

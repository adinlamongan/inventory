package com.adn.inventory.services;

import com.adn.inventory.dto.AppUserRequestDTO;
import com.adn.inventory.dto.GroupMenuResponseDTO;
import com.adn.inventory.models.AppUserQuery;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;

import java.util.List;

public interface AppUserService {
    DatatabelOutput<AppUserQuery> getAppUser(PagingRequest pagingRequest);

    AppUserQuery getAppUserByid(int userId);

    void addAppUser(AppUserRequestDTO dto);

    void editAppUser(AppUserRequestDTO dto);

    void deleteAppUser(AppUserRequestDTO dto);

    List<GroupMenuResponseDTO> getUserInfoRole(int userId);
}

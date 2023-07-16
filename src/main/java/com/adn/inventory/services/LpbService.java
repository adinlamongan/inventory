package com.adn.inventory.services;

import com.adn.inventory.dto.*;
import com.adn.inventory.models.Gudang;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;


import java.util.List;

public interface LpbService {
    DatatabelOutput<LpbHQueryResponseDTO> getListLpb(PagingRequest pagingRequest);

    LpbHQueryResponseDTO getLpbH(int id);

    List<LpbDQueryResponseDTO> getLpbD(int lpbId);

    void addLpb(LpbRequestDTO dto);

    void editLpb(int id, LpbRequestDTO dto);

    void deleteLpb(int id);

    List<LpbListPOHQueryResponseDTO> getListPO(String keyword);

    LpbListPODResponseDTO getListPOD(int poId);

    List<Gudang> getListGudang();

    void verifikasi(int id);

    void batalVerifikasi(int id);

}

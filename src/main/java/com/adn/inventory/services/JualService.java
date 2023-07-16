package com.adn.inventory.services;

import com.adn.inventory.dto.*;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;

import java.util.List;

public interface JualService {
    DatatabelOutput<JualHQueryResponseDTO> getListJual(PagingRequest pagingRequest);

    JualHQueryResponseDTO getJualH(int id);

    List<JualDQueryResponseDTO> getJualD(int jualId);

    void addJual(JualRequestDTO dto);

    void editJual(int id, JualRequestDTO dto);

    void deleteJual(int id, int suratJalanId);

    List<JualListSJQueryResponseDTO> getListSJ(String keyword);

    List<JualListProdukSJResponseDTO> getListBarangSJ(int suratJalanId);


    void verifikasi(int id);

    void batalVerifikasi(int id);

}

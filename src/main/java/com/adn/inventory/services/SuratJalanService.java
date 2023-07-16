package com.adn.inventory.services;

import com.adn.inventory.dto.*;
import com.adn.inventory.models.Gudang;
import com.adn.inventory.models.StokGudang;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;

import java.util.List;

public interface SuratJalanService {
    DatatabelOutput<SuratJalanHQueryResponseDTO> getListSuratJalan(PagingRequest pagingRequest);

    SuratJalanHQueryResponseDTO getSuratJalanH(int id);

    List<SuratJalanDQueryResponseDTO> getSuratJalanD(int suratJalanId);

    void addSuratJalan(SuratJalanRequestDTO dto);

    void editSuratJalan(int id, SuratJalanRequestDTO dto);

    void deleteSuratJalan(int id, int soId);

    List<SuratJalanListSOResponseDTO> getListSO(String keyword);

    SuratJalanListSODResponseDTO getListSOD(int soId);

    List<Gudang> getListGudang();
    StokGudang getStokGudangByProdukIdAndGudangId(int produkId, int gudangId);

    void verifikasi(int id);

    void batalVerifikasi(int id);

}

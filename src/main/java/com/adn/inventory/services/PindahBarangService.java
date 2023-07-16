package com.adn.inventory.services;

import com.adn.inventory.dto.*;
import com.adn.inventory.models.Gudang;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;

import java.util.List;

public interface PindahBarangService {
    DatatabelOutput<PindahBarangHQueryResponseDTO> getListPindahBarang(PagingRequest pagingRequest);

    PindahBarangHQueryResponseDTO getPindahBarangH(int id);

    List<PindahBarangDQueryResponseDTO> getPindahBarangD(int pindahBarangId);

    void addPindahBarang(PindahBarangRequestDTO dto);

    void editPindahBarang(int id, PindahBarangRequestDTO dto);

    void deletePindahBarang(int id);

    List<Gudang> getListGudang();

    List<PindahBarangAutocompleteBarangByGudangAsalResponseDTO> getListBarangGudangAsal(String keyword, int gudangAsalId);

    List<PindahBarangAutocompleteBarangByGudangAsalResponseDTO> getListBarangGudangAsalEdit(String keyword, int gudangAsalId, int pindahBarangId);


    void verifikasi(int id);

    void batalVerifikasi(int id);

}

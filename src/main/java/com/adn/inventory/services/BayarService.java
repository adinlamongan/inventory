package com.adn.inventory.services;

import com.adn.inventory.dto.*;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;

import java.util.List;

public interface BayarService {
    DatatabelOutput<BayarHQueryResponseDTO> getListBayar(PagingRequest pagingRequest);

    BayarHQueryResponseDTO getBayarH(int id);

    List<BayarDQueryResponseDTO> getBayarD(int bayarId);

    void addJual(BayarRequestDTO dto);

    void editBayar(int id, BayarRequestDTO dto);

    void deleteBayar(int id);

    List<BayarListInvoiceQueryResponseDTO> getListInvoice(int customerId, int bayarId);


    void verifikasi(int id);

    void batalVerifikasi(int id);

}

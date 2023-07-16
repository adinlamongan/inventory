package com.adn.inventory.services;



import com.adn.inventory.dto.PODQueryResponseDTO;
import com.adn.inventory.dto.POHQueryResponseDTO;
import com.adn.inventory.dto.PORequestDTO;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;

import java.util.List;

public interface PurchaseOrderService {
    DatatabelOutput<POHQueryResponseDTO> getListPurchaseOrder(PagingRequest pagingRequest);

    POHQueryResponseDTO getPurchaseOrderH(int id);

    List<PODQueryResponseDTO> getPurchaseOrderD(int purchaseOrderId);

    void addPurchaseOrder(PORequestDTO dto);

    void editPurchaseOrder(int id, PORequestDTO dto);

    void deletePurchaseOrder(int id);

    void verifikasi(int id);

    void batalVerifikasi(int id);

    int updateHarga();

    int updateHarga2();


}

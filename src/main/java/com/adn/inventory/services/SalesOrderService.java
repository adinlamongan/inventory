package com.adn.inventory.services;



import com.adn.inventory.dto.*;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;

import java.util.List;

public interface SalesOrderService {
    DatatabelOutput<SOHQueryResponseDTO> getListSalesOrder(PagingRequest pagingRequest);

    SOHQueryResponseDTO getSalesOrderH(int id);

    List<SODQueryResponseDTO> getSalesOrderD(int salesOrderId);

    void addSalesOrder(SORequestDTO dto);

    void editSalesOrder(int id, SORequestDTO dto);

    void deleteSalesOrder(int id);

    void verifikasi(int id);

    void batalVerifikasi(int id);



}

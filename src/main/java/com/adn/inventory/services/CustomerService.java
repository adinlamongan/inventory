package com.adn.inventory.services;

import com.adn.inventory.models.Customer;
import com.adn.inventory.models.Supplier;
import com.adn.inventory.dto.CustomerRequestDTO;
import com.adn.inventory.dto.CustomerResponseDTO;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;

import java.util.List;

public interface CustomerService {
    DatatabelOutput<Supplier> getCustomer(PagingRequest pagingRequest);

    Customer getCustomerByid(int supplierId);

    int editCustomer(CustomerRequestDTO dto);

    int addCustomer(CustomerRequestDTO dto);

    int deleteCustomer(CustomerRequestDTO dto);

    List<CustomerResponseDTO> getListCustomer(String keyword);



}

package com.adn.inventory.services.impl;

import com.adn.inventory.models.Customer;
import com.adn.inventory.models.Supplier;
import com.adn.inventory.repository.CustomerRepo;
import com.adn.inventory.services.CustomerService;
import com.adn.inventory.dto.CustomerRequestDTO;
import com.adn.inventory.dto.CustomerResponseDTO;
import com.adn.inventory.util.PageDataTable;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private PageDataTable pageDataTable;

    private ModelMapper modelMapper;

    private CustomerRepo customerRepo;


    @Override
    public DatatabelOutput<Supplier> getCustomer(PagingRequest pagingRequest) {
        return null;
    }

    @Override
    public Customer getCustomerByid(int supplierId) {
        return null;
    }

    @Override
    public int editCustomer(CustomerRequestDTO dto) {
        return 0;
    }

    @Override
    public int addCustomer(CustomerRequestDTO dto) {
        return 0;
    }

    @Override
    public int deleteCustomer(CustomerRequestDTO dto) {
        return 0;
    }

    @Override
    public List<CustomerResponseDTO> getListCustomer(String keyword) {
        return customerRepo.findTop10ByKodeContainingOrNamaContainingAllIgnoreCase(keyword,keyword);
    }
}

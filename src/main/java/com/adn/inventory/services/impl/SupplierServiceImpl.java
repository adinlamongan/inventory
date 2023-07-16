package com.adn.inventory.services.impl;

import com.adn.inventory.dto.SupplierRequestDTO;
import com.adn.inventory.dto.SupplierResponseDTO;
import com.adn.inventory.exceptions.ResourceNotFoundException;
import com.adn.inventory.exceptions.ResourceNotFoundExceptionForHtml;
import com.adn.inventory.models.Supplier;
import com.adn.inventory.models.Todo;
import com.adn.inventory.repository.SupplierRepo;
import com.adn.inventory.repository.TodoRepo;
import com.adn.inventory.services.SupplierService;
import com.adn.inventory.util.PageDataTable;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.PagingRequest;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private PageDataTable pageDataTable;

    @Autowired
    private SupplierRepo supplierRepo;
    @Autowired
    private TodoRepo todoRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DatatabelOutput<Supplier> getSupplier(PagingRequest pagingRequest) {
        Pageable pageable = pageDataTable.pagination(pagingRequest);

        Page<Supplier> suppliers = null;

        String keyword = pagingRequest.getSearch().getValue();
        if(keyword.isEmpty()){
            suppliers = supplierRepo.findAll(pageable);
        }else {
            suppliers = supplierRepo.findByKodeContainingOrNamaContainingOrAlamatContainingAllIgnoreCase(keyword, keyword, keyword, pageable);
        }
        List<Supplier> supplierList = suppliers.getContent();
        DatatabelOutput<Supplier> result = new DatatabelOutput<>();
        result.setData(supplierList);
        result.setDraw(pagingRequest.getDraw());
        result.setRecordsTotal(supplierRepo.count());
        result.setRecordsFiltered(suppliers.getTotalElements());
        return result;
    }

    @Override
    public Supplier getSupplierByid(int supplierId) {
        return supplierRepo.findById(supplierId).orElseThrow(()-> new ResourceNotFoundExceptionForHtml("invalid id"));
    }

    @Override
    @Transactional
    public void addSupplier(SupplierRequestDTO dto) {
        Supplier supplier = new Supplier();
//        modelMapper.typeMap(SupplierRequestDTO.class, Supplier.class).addMappings(m-> {
//            m.skip(Supplier::setVersion);
//            m.skip(Supplier::setCreatedAt);
//            m.skip(Supplier::setUpdatedAt);
//            m.skip(Supplier::setUpdatedBy);
//            m.skip(Supplier::setUpdatedAt);
//        });
//        supplier = modelMapper.map(dto,Supplier.class);


        supplier.setKode(dto.getKode());
        supplier.setNama(dto.getNama());
        supplier.setAlamat(dto.getAlamat());
        supplier.setFax(dto.getFax());
        supplier.setTelepon(dto.getTelepon());
        supplierRepo.save(supplier);
    }

    @Override
    @Transactional
    public void editSupplier(int id, SupplierRequestDTO dto) {

        Supplier supplier = supplierRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("invalid id"));

//        supplier = modelMapper.map(dto, Supplier.class);
        supplier.setKode(dto.getKode());
        supplier.setNama(dto.getNama());
        supplier.setAlamat(dto.getAlamat());
        supplier.setFax(dto.getFax());
        supplier.setTelepon(dto.getTelepon());
        supplierRepo.save(supplier);
    }

    @Override
    @Transactional
    public void deleteSupplier(int id) {
        supplierRepo.deleteById(id);
    }

    @Override
    public List<SupplierResponseDTO> getListSupplier(String keyword) {
        return supplierRepo.findTop10ByKodeContainingOrNamaContainingAllIgnoreCase(keyword, keyword);
    }

    @Override
    public void exportExcel() {

    }

    @Override
    @Transactional
    public void insertBatch() {

        int totalObjects = 10000;

        long start = System.currentTimeMillis();

        List<Todo> books = new ArrayList<>();
        for (int i = 0; i < totalObjects; i++) {
            Todo todo = new Todo();
            todo.setTodo("todo" + i);
            todo.setPrice(i);
            books.add(todo);
        }


        System.out.println("Finished creating "+totalObjects+" objects in memory in:" + (System.currentTimeMillis() - start)/1000);

        start = System.currentTimeMillis();
        System.out.println("Inserting ..........");



        todoRepo.saveAll(books);

        System.out.println("Finished inserting "+totalObjects+" objects in :" + (System.currentTimeMillis() - start));
    }

    @Override
    @Transactional
    public void insert() {
        int totalObjects = 10000;

        long start = System.currentTimeMillis();




        System.out.println("Finished tidak creating "+totalObjects+" objects in memory in:" + (System.currentTimeMillis() - start)/1000);

        start = System.currentTimeMillis();
        System.out.println("Inserting ..........");

        //List<Todo> books = new ArrayList<>();
        for (int i = 0; i < totalObjects; i++) {
            Todo todo = new Todo();
            todo.setTodo("todo" + i);
            todo.setPrice(i);
           todoRepo.save(todo);
        }

        //todoRepo.saveAll(books);

        System.out.println("Finished tidak inserting "+totalObjects+" objects in :" + (System.currentTimeMillis() - start));
    }

}

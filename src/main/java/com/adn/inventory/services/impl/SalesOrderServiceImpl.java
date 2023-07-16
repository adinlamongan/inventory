package com.adn.inventory.services.impl;

import com.adn.inventory.dto.SODQueryResponseDTO;
import com.adn.inventory.dto.SOHQueryResponseDTO;
import com.adn.inventory.dto.SORequestDTO;
import com.adn.inventory.exceptions.ResourceInfoException;
import com.adn.inventory.exceptions.ResourceNotFoundException;
import com.adn.inventory.models.*;
import com.adn.inventory.repository.SalesOrderDRepo;
import com.adn.inventory.repository.SalesOrderHRepo;
import com.adn.inventory.repository.SalesOrderQueryRepo;
import com.adn.inventory.repository.SuratJalanHRepo;
import com.adn.inventory.security.CustomUser;
import com.adn.inventory.services.SalesOrderService;
import com.adn.inventory.util.NomorOtomatis;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.Order;
import com.adn.inventory.util.paging.PagingRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@Service
@AllArgsConstructor
public class SalesOrderServiceImpl implements SalesOrderService {

    private NomorOtomatis nomorOtomatis;

    private SalesOrderQueryRepo salesOrderQueryRepo;

    private SalesOrderHRepo salesOrderHRepo;

    private SalesOrderDRepo salesOrderDRepo;

    private SuratJalanHRepo suratJalanHRepo;


    @Override
    public DatatabelOutput<SOHQueryResponseDTO> getListSalesOrder(PagingRequest pagingRequest) {
        int pageSize = pagingRequest.getLength();
        int pageNumber = pagingRequest.getStart() == 0 ? 0 : pagingRequest.getStart() / pageSize;

        String keyword = pagingRequest.getSearch().getValue();

        Order order = pagingRequest.getOrder().get(0);
        int columnIndex = order.getColumn();
        String sortBy = pagingRequest.getColumns().get(columnIndex).getData();
        String direction = order.getDir().name();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<SOHQueryResponseDTO> salesOrder = salesOrderQueryRepo.findSalesOrder(keyword, sortBy, direction,
                pageable);

        List<SOHQueryResponseDTO> salesOrderList = salesOrder.getContent();
        DatatabelOutput<SOHQueryResponseDTO> result = new DatatabelOutput<>();
        result.setData(salesOrderList);
        result.setDraw(pagingRequest.getDraw());
        result.setRecordsTotal(salesOrderQueryRepo.getTotalRecord());
        result.setRecordsFiltered(salesOrder.getTotalElements());
        return result;
    }

    @Override
    public SOHQueryResponseDTO getSalesOrderH(int id) {
        return salesOrderHRepo.findHeaderById(id);
    }

    @Override
    public List<SODQueryResponseDTO> getSalesOrderD(int salesOrderId) {
        return salesOrderDRepo.findDetailDBySalesOrderId(salesOrderId);
    }

    @Override
    @Transactional
    public void addSalesOrder(SORequestDTO dto) {
        SalesOrderH salesOrderH = new SalesOrderH();
        String nomor = nomorOtomatis.getNomor(dto.getTanggal(), "sales_orderh");
        salesOrderH.setNomor(nomor);
        salesOrderH.setTanggal(dto.getTanggal());
        salesOrderH.setCustomerId(dto.getCustomerId());
        salesOrderH.setTotal(dto.getTotal());
        salesOrderH.setKeterangan(dto.getKeterangan());
        salesOrderHRepo.save(salesOrderH);
        int length = dto.getProdukId().size();
        for (int i = 0; i < length; i++) {
            SalesOrderD salesOrderD = new SalesOrderD();
            salesOrderD.setSalesOrderId(salesOrderH.getId());
            salesOrderD.setProdukId(dto.getProdukId().get(i));
            salesOrderD.setHarga(dto.getHarga().get(i));
            salesOrderD.setQty(dto.getQty().get(i));
            salesOrderD.setTotal(dto.getTotalDetail().get(i));
            salesOrderDRepo.save(salesOrderD);
        }


    }

    @Override
    @Transactional
    public void editSalesOrder(int id, SORequestDTO dto) {
        SalesOrderH salesOrderH = salesOrderHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid po id"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dto.getTanggal());
        int dtoDate = calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH);
        calendar.setTime(salesOrderH.getTanggal());
        int entTanggal = calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH);
        if (dtoDate != entTanggal) {
            String nomor = nomorOtomatis.getNomor(dto.getTanggal(), "sales_orderh");
            salesOrderH.setNomor(nomor);
        }
        salesOrderH.setTanggal(dto.getTanggal());
        salesOrderH.setCustomerId(dto.getCustomerId());
        salesOrderH.setTotal(dto.getTotal());
        salesOrderH.setKeterangan(dto.getKeterangan());
        salesOrderHRepo.save(salesOrderH);
        salesOrderDRepo.deleteBySalesOrderId(id);
        int length = dto.getProdukId().size();
        for (int i = 0; i < length; i++) {
            SalesOrderD salesOrderD = new SalesOrderD();
            salesOrderD.setSalesOrderId(salesOrderH.getId());
            salesOrderD.setProdukId(dto.getProdukId().get(i));
            salesOrderD.setHarga(dto.getHarga().get(i));
            salesOrderD.setQty(dto.getQty().get(i));
            salesOrderD.setTotal(dto.getTotalDetail().get(i));
            salesOrderDRepo.save(salesOrderD);
        }

    }

    @Override
    @Transactional
    public void deleteSalesOrder(int id) {
        salesOrderDRepo.deleteBySalesOrderId(id);
        salesOrderHRepo.deleteById(id);

    }

    @Override
    @Transactional
    public void verifikasi(int id) {
        SalesOrderH salesOrderH = salesOrderHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid so id"));
        if (salesOrderH.getVerifikasi()) {
            throw new ResourceInfoException("Sudah terverifikasi");
        }
        salesOrderH.setVerifikasi(Boolean.TRUE);
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        salesOrderH.setVerifikasiBy(user.getUserId());
        salesOrderH.setVerifikasiAt(LocalDateTime.now());
        salesOrderHRepo.save(salesOrderH);

    }

    @Override
    @Transactional
    public void batalVerifikasi(int id) {
        SalesOrderH salesOrderH = salesOrderHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid so id"));
        if (!salesOrderH.getVerifikasi()) {
            throw new ResourceInfoException("Verifikasi sudah di batalkan");
        }
        if (salesOrderH.getTerpakai()) {
            throw new ResourceInfoException("Sudah terpakai di surat jalan. Verifikasi tidak bisa di batalkan");
        }
        salesOrderH.setVerifikasi(Boolean.FALSE);
        salesOrderH.setVerifikasiBy(0);
        salesOrderH.setVerifikasiAt(null);
        salesOrderHRepo.save(salesOrderH);

    }
}

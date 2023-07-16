package com.adn.inventory.services.impl;

import com.adn.inventory.dto.PODQueryResponseDTO;
import com.adn.inventory.dto.POHQueryResponseDTO;
import com.adn.inventory.dto.PORequestDTO;
import com.adn.inventory.exceptions.ResourceInfoException;
import com.adn.inventory.exceptions.ResourceNotFoundException;
import com.adn.inventory.models.PurchaseOrderD;
import com.adn.inventory.models.PurchaseOrderH;
import com.adn.inventory.models.Supplier;
import com.adn.inventory.repository.PurchaseOrderDRepo;
import com.adn.inventory.repository.PurchaseOrderHRepo;
import com.adn.inventory.repository.PurchaseOrderQueryRepo;
import com.adn.inventory.security.CustomUser;
import com.adn.inventory.services.PurchaseOrderService;
import com.adn.inventory.util.Config;
import com.adn.inventory.util.NomorOtomatis;
import com.adn.inventory.util.PageDataTable;
import com.adn.inventory.util.paging.DatatabelOutput;
import com.adn.inventory.util.paging.Order;
import com.adn.inventory.util.paging.PagingRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@Service
@AllArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private NomorOtomatis nomorOtomatis;

    private PurchaseOrderQueryRepo purchaseOrderQueryRepo;

    private PurchaseOrderHRepo purchaseOrderHRepo;

    private PurchaseOrderDRepo purchaseOrderDRepo;

    private Config config;

    @Override
    public DatatabelOutput<POHQueryResponseDTO> getListPurchaseOrder(PagingRequest pagingRequest) {
        int pageSize = pagingRequest.getLength();
        int pageNumber = pagingRequest.getStart() == 0 ? 0 : pagingRequest.getStart() / pageSize;

        String keyword = pagingRequest.getSearch().getValue();

        Order order = pagingRequest.getOrder().get(0);
        int columnIndex = order.getColumn();
        String sortBy = pagingRequest.getColumns().get(columnIndex).getData();
        String direction = order.getDir().name();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<POHQueryResponseDTO> purchaseOrder = purchaseOrderQueryRepo.findPurchaseOrder(keyword, sortBy, direction,
                pageable);

        List<POHQueryResponseDTO> purchaseOrderList = purchaseOrder.getContent();
        DatatabelOutput<POHQueryResponseDTO> result = new DatatabelOutput<>();
        result.setData(purchaseOrderList);
        result.setDraw(pagingRequest.getDraw());
        result.setRecordsTotal(purchaseOrderQueryRepo.getTotalRecord());
        result.setRecordsFiltered(purchaseOrder.getTotalElements());
        return result;

    }

    @Override
    public POHQueryResponseDTO getPurchaseOrderH(int id) {
        return purchaseOrderHRepo.findHeaderById(id);
    }

    @Override
    public List<PODQueryResponseDTO> getPurchaseOrderD(int purchaseOrderId) {
        //return purchaseOrderQueryRepo.findPurchaseOrderDByPurchaseOrderId(purchaseOrderId);
        return purchaseOrderDRepo.findDetailByPurchaseOrderId(purchaseOrderId);
    }

    @Override
    @Transactional
    public void addPurchaseOrder(PORequestDTO dto) {
        PurchaseOrderH purchaseOrderH = new PurchaseOrderH();
        String nomor = nomorOtomatis.getNomor(dto.getTanggal(), "purchase_orderh");
        purchaseOrderH.setNomor(nomor);
        purchaseOrderH.setTanggal(dto.getTanggal());
        purchaseOrderH.setSupplierId(dto.getSupplierId());
        purchaseOrderH.setTotal(dto.getTotal());
        purchaseOrderH.setKeterangan(dto.getKeterangan());
        purchaseOrderHRepo.save(purchaseOrderH);
        int length = dto.getProdukId().size();
        for (int i = 0; i < length; i++) {
            PurchaseOrderD purchaseOrderD = new PurchaseOrderD();
            purchaseOrderD.setPurchaseOrderId(purchaseOrderH.getId());
            purchaseOrderD.setProdukId(dto.getProdukId().get(i));
            purchaseOrderD.setHarga(dto.getHarga().get(i));
            purchaseOrderD.setQty(dto.getQty().get(i));
            purchaseOrderD.setTotal(dto.getTotalDetail().get(i));
            purchaseOrderDRepo.save(purchaseOrderD);
        }


    }

    @Override
    @Transactional
    public void editPurchaseOrder(int id, PORequestDTO dto) {
        PurchaseOrderH purchaseOrderH = purchaseOrderHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid po id"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dto.getTanggal());
        int dtoDate = calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH);
        calendar.setTime(purchaseOrderH.getTanggal());
        int entTanggal = calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH);
        if (dtoDate != entTanggal) {
            String nomor = nomorOtomatis.getNomor(dto.getTanggal(), "purchase_orderh");
            purchaseOrderH.setNomor(nomor);
        }
        purchaseOrderH.setTanggal(dto.getTanggal());
        purchaseOrderH.setSupplierId(dto.getSupplierId());
        purchaseOrderH.setTotal(dto.getTotal());
        purchaseOrderH.setKeterangan(dto.getKeterangan());
        purchaseOrderHRepo.save(purchaseOrderH);
        purchaseOrderDRepo.deleteByPurchaseOrderId(id);
        int length = dto.getProdukId().size();
        for (int i = 0; i < length; i++) {
            PurchaseOrderD purchaseOrderD = new PurchaseOrderD();
            purchaseOrderD.setPurchaseOrderId(purchaseOrderH.getId());
            purchaseOrderD.setProdukId(dto.getProdukId().get(i));
            purchaseOrderD.setHarga(dto.getHarga().get(i));
            purchaseOrderD.setQty(dto.getQty().get(i));
            purchaseOrderD.setTotal(dto.getTotalDetail().get(i));
            purchaseOrderDRepo.save(purchaseOrderD);
        }

    }

    @Override
    @Transactional
    public void deletePurchaseOrder(int id) {
        purchaseOrderDRepo.deleteByPurchaseOrderId(id);
        purchaseOrderHRepo.deleteById(id);

    }

    @Override
    @Transactional
    public void verifikasi(int id) {
        PurchaseOrderH purchaseOrderH = purchaseOrderHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid po id"));
        if (purchaseOrderH.getVerifikasi()) {
            throw new ResourceInfoException("Sudah terverifikasi");
        }
        purchaseOrderH.setVerifikasi(Boolean.TRUE);
        purchaseOrderH.setVerifikasiAt(LocalDateTime.now());
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        purchaseOrderH.setVerifikasiBy(user.getUserId());
        purchaseOrderHRepo.save(purchaseOrderH);

    }

    @Override
    @Transactional
    public void batalVerifikasi(int id) {
        if (purchaseOrderQueryRepo.cekTerpakai(id)) {
            throw new ResourceInfoException("Po sudah terpakai");
        }
        PurchaseOrderH purchaseOrderH = purchaseOrderHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid po id"));
        if (!purchaseOrderH.getVerifikasi()) {
            throw new ResourceInfoException("Verifikasi sudah di batalkan");
        }
        purchaseOrderH.setVerifikasi(Boolean.FALSE);
        purchaseOrderH.setVerifikasiAt(null);
        purchaseOrderH.setVerifikasiBy(0);
        purchaseOrderHRepo.save(purchaseOrderH);

    }

    @SneakyThrows
    @Override
    @Transactional
    public int updateHarga() {
        PurchaseOrderD d = purchaseOrderDRepo.findById(1).orElseThrow(()->new ResourceNotFoundException("invalid id"));
        if (d.getHarga() < 1000){
            throw new ResourceInfoException("aaaa");
        }
        d.setHarga(500);
        purchaseOrderDRepo.save(d);
        Thread.sleep(10000);
        return (int) d.getHarga();
    }

    @Override
    @Transactional
    public int updateHarga2() {
        PurchaseOrderD d = purchaseOrderDRepo.findById(1).orElseThrow(()->new ResourceNotFoundException("invalid id"));
       if (d.getHarga() < 1000){
           throw new ResourceInfoException("bb");
       }
       d.setHarga(700);
       purchaseOrderDRepo.save(d);
        return (int) d.getHarga();
    }
}

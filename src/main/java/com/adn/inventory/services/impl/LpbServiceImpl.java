package com.adn.inventory.services.impl;

import com.adn.inventory.dto.*;
import com.adn.inventory.exceptions.ResourceInfoException;
import com.adn.inventory.exceptions.ResourceNotFoundException;
import com.adn.inventory.models.*;
import com.adn.inventory.repository.*;
import com.adn.inventory.security.CustomUser;
import com.adn.inventory.services.LpbService;
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
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class LpbServiceImpl implements LpbService {

    private LpbQueryRepo lpbQueryRepo;

    private LpbHRepo lpbHRepo;

    private LpbDRepo lpbDRepo;

    private GudangRepo gudangRepo;

    private NomorOtomatis nomorOtomatis;

    private PurchaseOrderDRepo purchaseOrderDRepo;
    private PurchaseOrderHRepo purchaseOrderHRepo;

    private StokGudangRepo stokGudangRepo;


    @Override
    public DatatabelOutput<LpbHQueryResponseDTO> getListLpb(PagingRequest pagingRequest) {
        int pageSize = pagingRequest.getLength();
        int pageNumber = pagingRequest.getStart() == 0 ? 0 : pagingRequest.getStart() / pageSize;

        String keyword = pagingRequest.getSearch().getValue();

        Order order = pagingRequest.getOrder().get(0);
        int columnIndex = order.getColumn();
        String sortBy = pagingRequest.getColumns().get(columnIndex).getData();
        String direction = order.getDir().name();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<LpbHQueryResponseDTO> lpb = lpbQueryRepo.findLpb(keyword, sortBy, direction,
                pageable);

        List<LpbHQueryResponseDTO> lpbList = lpb.getContent();
        DatatabelOutput<LpbHQueryResponseDTO> result = new DatatabelOutput<>();
        result.setData(lpbList);
        result.setDraw(pagingRequest.getDraw());
        result.setRecordsTotal(lpbQueryRepo.getTotalRecord());
        result.setRecordsFiltered(lpb.getTotalElements());
        return result;
    }

    @Override
    public LpbHQueryResponseDTO getLpbH(int id) {
        return lpbHRepo.findHeaderHById(id);
    }

    @Override
    public List<LpbDQueryResponseDTO> getLpbD(int lpbId) {
        return lpbDRepo.findDetailByLpbId(lpbId);
    }

    @Override
    @Transactional
    public void addLpb(LpbRequestDTO dto) {
        LpbH lpbH = new LpbH();
        String nomor = nomorOtomatis.getNomor(dto.getTanggal(), "lpbh");
        lpbH.setNomor(nomor);
        lpbH.setTanggal(dto.getTanggal());
        lpbH.setPurchaseOrderId(dto.getPurchaseOrderId());
        lpbH.setKeterangan(dto.getKeterangan());
        lpbHRepo.save(lpbH);

        int length = dto.getProdukId().size();
        for (int i = 0; i < length; i++) {
            LpbD lpbD = new LpbD();
            lpbD.setLpbId(lpbH.getId());
            lpbD.setPurchaseOrderDId(dto.getPurchaseOrderDetailId().get(i));
            lpbD.setProdukId(dto.getProdukId().get(i));
            lpbD.setGudangId(dto.getGudangId().get(i));
            lpbD.setQty(dto.getQty().get(i));
            lpbDRepo.save(lpbD);
            PurchaseOrderD purchaseOrderD = purchaseOrderDRepo.findAndLockById(lpbD.getPurchaseOrderDId()).orElseThrow(() -> new ResourceNotFoundException("invalid po detail id"));
            purchaseOrderD.setQtyTerima(dto.getQty().get(i) + purchaseOrderD.getQtyTerima());
            purchaseOrderDRepo.save(purchaseOrderD);
        }
        purchaseOrderHRepo.updateHabisPO(lpbH.getPurchaseOrderId());


    }

    @Override
    @Transactional
    public void editLpb(int id, LpbRequestDTO dto) {
        LpbH lpbH = lpbHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid lpb id"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dto.getTanggal());
        int dtoDate = calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH);
        calendar.setTime(lpbH.getTanggal());
        int entTanggal = calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH);
        if (dtoDate != entTanggal) {
            String nomor = nomorOtomatis.getNomor(dto.getTanggal(), "lpbh");
            lpbH.setNomor(nomor);
        }
        lpbH.setTanggal(dto.getTanggal());
        lpbH.setKeterangan(dto.getKeterangan());
        lpbHRepo.save(lpbH);

        int length = dto.getProdukId().size();
        for (int i = 0; i < length; i++) {
            LpbD lpbD = lpbDRepo.findById(dto.getDetailId().get(i)).orElseThrow(() -> new ResourceNotFoundException("invalid detail lpb id"));
            double oldQtyLpb = lpbD.getQty();
            lpbD.setGudangId(dto.getGudangId().get(i));
            lpbD.setQty(dto.getQty().get(i));
            lpbDRepo.save(lpbD);
            PurchaseOrderD purchaseOrderD = purchaseOrderDRepo.findAndLockById(lpbD.getPurchaseOrderDId()).orElseThrow(() -> new ResourceNotFoundException("invalid po detail id"));
            purchaseOrderD.setQtyTerima(dto.getQty().get(i) - oldQtyLpb + purchaseOrderD.getQtyTerima());
            purchaseOrderDRepo.save(purchaseOrderD);
        }
        purchaseOrderHRepo.updateHabisPO(lpbH.getPurchaseOrderId());

    }

    @Override
    @Transactional
    public void deleteLpb(int id) {
        List<LpbD> detail = lpbDRepo.findByLpbId(id);
        if (detail.isEmpty()) {
            throw new ResourceNotFoundException("invalid lpb id");
        }

        AtomicInteger poId = new AtomicInteger();
        detail.forEach(e -> {
            PurchaseOrderD purchaseOrderD = purchaseOrderDRepo.findAndLockById(e.getPurchaseOrderDId()).orElseThrow(() -> new
                    ResourceNotFoundException("invalid po detail id"));
            purchaseOrderD.setQtyTerima(purchaseOrderD.getQtyTerima() - e.getQty());
            purchaseOrderDRepo.save(purchaseOrderD);
            poId.set(purchaseOrderD.getPurchaseOrderId());
        });

        int res = purchaseOrderHRepo.updateHabisPO(poId.get());
        if (res == 0) {
            throw new ResourceNotFoundException("invalid po id");
        }
        lpbDRepo.deleteByLpbId(id);
        lpbHRepo.deleteById(id);

    }

    @Override
    public List<LpbListPOHQueryResponseDTO> getListPO(String keyword) {
        return lpbHRepo.findPurchaseOrderByLike("%" + keyword + "%");
    }

    @Override
    public LpbListPODResponseDTO getListPOD(int poId) {
        List<Gudang> gudangs = (List<Gudang>) gudangRepo.findAll();
        LpbListPODResponseDTO dto = new LpbListPODResponseDTO();
        dto.setListProdukPO(lpbDRepo.findPurchaseOrderDByPurchaseOrderId(poId));
        dto.setGudangs(gudangs);
        return dto;
    }

    @Override
    public List<Gudang> getListGudang() {
        return (List<Gudang>) gudangRepo.findAll();
    }


    @Override
    @Transactional
    public void verifikasi(int id) {
        LpbH lpbH = lpbHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid lpb id"));
        if (lpbH.getVerifikasi()) {
            throw new ResourceInfoException("sudah terverifikasi");
        }

        List<LpbD> detail = lpbDRepo.findAllByLpbId(id);
        if (detail.isEmpty()) {
            throw new ResourceNotFoundException("invalid lpb id");
        }
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        lpbH.setVerifikasiBy(user.getUserId());
        lpbH.setVerifikasiAt(LocalDateTime.now());
        lpbH.setVerifikasi(Boolean.TRUE);
        lpbHRepo.save(lpbH);
        detail.forEach(e ->{
            StokGudang stokGudang = stokGudangRepo.findAndLockByProodukIdAndGudangId(e.getProdukId(), e.getGudangId());
            if (stokGudang == null) {
                StokGudang insert = new StokGudang();
                insert.setGudangId(e.getGudangId());
                insert.setProdukId(e.getProdukId());
                insert.setQty(e.getQty());
                stokGudangRepo.save(insert);
            }else{
                stokGudang.setQty(stokGudang.getQty() + e.getQty());
                stokGudangRepo.save(stokGudang);
            }
        });
//        List<Stok> stokList = detail.stream().map(e -> {
//            Stok stok = new Stok();
//            stok.setProdukId(e.getProdukId());
//            stok.setGudangId(e.getGudangId());
//            stok.setRerenceId(e.getLpbId());
//            stok.setTableName("lpbh");
//            stok.setRerenceDetailId(e.getId());
//            stok.setTableNameDetail("lpbd");
//            stok.setQty(e.getQty());
//            return stok;
//        }).collect(Collectors.toList());
//        stokRepo.saveAll(stokList);

    }

    @Override
    @Transactional
    public void batalVerifikasi(int id) {
        LpbH lpbH = lpbHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid lpb id"));
        if (!lpbH.getVerifikasi()) {
            throw new ResourceInfoException("Verifikasi sudah di batalkan");
        }

        List<LpbD> detail = lpbDRepo.findAllByLpbId(id);
        if (detail.isEmpty()) {
            throw new ResourceNotFoundException("invalid lpb id");
        }
        detail.forEach(e ->{
            StokGudang stokGudang = stokGudangRepo.findAndLockByProodukIdAndGudangId(e.getProdukId(), e.getGudangId());
            if (stokGudang == null) {
                throw new ResourceNotFoundException("invalid produk id="+e.getProdukId() +" and gudang id=" + e.getGudangId());
            }else{
                if (stokGudang.getQty() < e.getQty()){
                    throw new ResourceInfoException("gudangid =" + stokGudang.getGudangId() + " produkid =" + stokGudang.getProdukId() + " stok minus" );
                }
                stokGudang.setQty(stokGudang.getQty() - e.getQty());
                stokGudangRepo.save(stokGudang);
            }
        });

//        Stok stok = stokRepo.getStokByReferenceIdAndTableName(lpbH.getId(), "lpbh");
//        if (stok != null && stok.getTerpakai() > 0) {
//            throw new RuntimeException("stok sudah terpakai");
//        }
//
//        stokRepo.deleteStokByReferenceIdAndTableName(lpbH.getId(), "lpbh");
        lpbH.setVerifikasiBy(0);
        lpbH.setVerifikasiAt(null);
        lpbH.setVerifikasi(Boolean.FALSE);
        lpbHRepo.save(lpbH);

    }
}

package com.adn.inventory.services.impl;

import com.adn.inventory.dto.*;
import com.adn.inventory.exceptions.ResourceInfoException;
import com.adn.inventory.exceptions.ResourceNotFoundException;
import com.adn.inventory.models.*;
import com.adn.inventory.repository.*;
import com.adn.inventory.security.CustomUser;
import com.adn.inventory.services.SuratJalanService;
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
public class SuratJalanServiceImpl implements SuratJalanService {

    private SuratJalanQueryRepo suratJalanQueryRepo;

    private SuratJalanHRepo suratJalanHRepo;

    private SuratJalanDRepo suratJalanDRepo;

    private GudangRepo gudangRepo;

    private NomorOtomatis nomorOtomatis;

    private SalesOrderHRepo salesOrderHRepo;

    private StokGudangRepo stokGudangRepo;
    
    @Override
    public DatatabelOutput<SuratJalanHQueryResponseDTO> getListSuratJalan(PagingRequest pagingRequest) {
        int pageSize = pagingRequest.getLength();
        int pageNumber = pagingRequest.getStart() == 0 ? 0 : pagingRequest.getStart() / pageSize;

        String keyword = pagingRequest.getSearch().getValue();

        Order order = pagingRequest.getOrder().get(0);
        int columnIndex = order.getColumn();
        String sortBy = pagingRequest.getColumns().get(columnIndex).getData();
        String direction = order.getDir().name();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<SuratJalanHQueryResponseDTO> suratJalan = suratJalanQueryRepo.findSuratJalan(keyword, sortBy, direction,
                pageable);

        List<SuratJalanHQueryResponseDTO> suratJalanList = suratJalan.getContent();
        DatatabelOutput<SuratJalanHQueryResponseDTO> result = new DatatabelOutput<>();
        result.setData(suratJalanList);
        result.setDraw(pagingRequest.getDraw());
        result.setRecordsTotal(suratJalanQueryRepo.getTotalRecord());
        result.setRecordsFiltered(suratJalan.getTotalElements());
        return result;
    }

    @Override
    public SuratJalanHQueryResponseDTO getSuratJalanH(int id) {
        return suratJalanHRepo.findHeaderById(id);
    }

    @Override
    public List<SuratJalanDQueryResponseDTO> getSuratJalanD(int suratJalanId) {
        return suratJalanDRepo.findDetailBySuratJalanId(suratJalanId);
    }

    @Override
    @Transactional
    public void addSuratJalan(SuratJalanRequestDTO dto) {
        SuratJalanH suratJalanH = new SuratJalanH();
        String nomor = nomorOtomatis.getNomor(dto.getTanggal(), "surat_jalanh");
        suratJalanH.setNomor(nomor);
        suratJalanH.setTanggal(dto.getTanggal());
        suratJalanH.setSalesOrderId(dto.getSalesOrderId());
        suratJalanH.setCustomerId(dto.getCustomerId());
        suratJalanH.setKeterangan(dto.getKeterangan());
        suratJalanHRepo.save(suratJalanH);

        int length = dto.getProdukId().size();
        for (int i = 0; i < length; i++) {
            SuratJalanD suratJalanD = new SuratJalanD();
            suratJalanD.setSuratJalanId(suratJalanH.getId());
            suratJalanD.setSalesOrderDId(dto.getSalesOrderDetailId().get(i));
            suratJalanD.setProdukId(dto.getProdukId().get(i));
            suratJalanD.setGudangId(dto.getGudangId().get(i));
            suratJalanD.setStokId(dto.getStokId().get(i));
            suratJalanD.setQty(dto.getQty().get(i));
            suratJalanDRepo.save(suratJalanD);
            
        }
        SalesOrderH salesOrderH = salesOrderHRepo.findById(suratJalanH.getSalesOrderId()).orElseThrow(() -> new ResourceNotFoundException("invalid so id"));
        salesOrderH.setTerpakai(true);
        salesOrderHRepo.save(salesOrderH);
    }

    @Override
    @Transactional
    public void editSuratJalan(int id, SuratJalanRequestDTO dto) {
        SuratJalanH suratJalanH = suratJalanHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid lpb id"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dto.getTanggal());
        int dtoDate = calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH);
        calendar.setTime(suratJalanH.getTanggal());
        int entTanggal = calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH);
        if (dtoDate != entTanggal) {
            String nomor = nomorOtomatis.getNomor(dto.getTanggal(), "lpbh");
            suratJalanH.setNomor(nomor);
        }
        suratJalanH.setTanggal(dto.getTanggal());
        suratJalanH.setKeterangan(dto.getKeterangan());
        suratJalanHRepo.save(suratJalanH);

        int length = dto.getProdukId().size();
        for (int i = 0; i < length; i++) {
            SuratJalanD suratJalanD = suratJalanDRepo.findById(dto.getDetailId().get(i)).orElseThrow(() -> new ResourceNotFoundException("invalid detail sj id"));
            double oldQtySJ = suratJalanD.getQty();
            int oldGudangId = suratJalanD.getGudangId();
            suratJalanD.setGudangId(dto.getGudangId().get(i));
            suratJalanD.setStokId(dto.getStokId().get(i));
            suratJalanD.setQty(dto.getQty().get(i));
            suratJalanDRepo.save(suratJalanD);
        }
    }

    @Override
    @Transactional
    public void deleteSuratJalan(int id, int soId) {
        List<SuratJalanD> suratJalanD = suratJalanDRepo.findAllBySuratJalanId(id);
        suratJalanD.forEach(e -> {
            StokGudang stokGudang = stokGudangRepo.findAndLockByProodukIdAndGudangId(e.getProdukId(), e.getGudangId());
            if (stokGudang == null) {
                throw new ResourceNotFoundException("stok not found");
            } else {
                double sisa = stokGudang.getQty() + e.getQty();
                stokGudang.setQty(sisa);
                stokGudangRepo.save(stokGudang);
            }
        });
        suratJalanDRepo.deleteBySuratJalanId(id);
        suratJalanHRepo.deleteById(id);
        SalesOrderH salesOrderH = salesOrderHRepo.findById(soId).orElseThrow(() -> new ResourceNotFoundException("invalid so id"));
        salesOrderH.setTerpakai(false);
        salesOrderHRepo.save(salesOrderH);
    }

    @Override
    public List<SuratJalanListSOResponseDTO> getListSO(String keyword) {
        return suratJalanHRepo.findSalesOrderByLike("%" + keyword + "%");
    }

    @Override
    public SuratJalanListSODResponseDTO getListSOD(int soId) {
        List<Gudang> gudangs = (List<Gudang>) gudangRepo.findAll();
        SuratJalanListSODResponseDTO dto = new SuratJalanListSODResponseDTO();
        dto.setListProdukSO(suratJalanDRepo.findSalesOrderDBySalesOrderId(soId));
        dto.setGudangs(gudangs);
        return dto;
    }

    @Override
    public List<Gudang> getListGudang() {
        return (List<Gudang>) gudangRepo.findAll();
    }

    @Override
    public StokGudang getStokGudangByProdukIdAndGudangId(int produkId, int gudangId) {
        return stokGudangRepo.findByProdukIdAndGudangId(produkId, gudangId);
    }

    @Override
    @Transactional
    public void verifikasi(int id) {
        SuratJalanH suratJalanH = suratJalanHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid sj id"));
        if (suratJalanH.getVerifikasi()) {
            throw new RuntimeException("sudah terverifikasi");
        }
        suratJalanH.setVerifikasi(Boolean.TRUE);
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        suratJalanH.setVerifikasiBy(user.getUserId());
        suratJalanH.setVerifikasiAt(LocalDateTime.now());
        suratJalanHRepo.save(suratJalanH);
        List<SuratJalanD> suratJalanD = suratJalanDRepo.findAllBySuratJalanId(id);
        if (suratJalanD == null) {
            throw new ResourceNotFoundException("invalid sj id");
        }
        suratJalanD.forEach( e -> {
            StokGudang stokGudang = stokGudangRepo.findAndLockById(e.getStokId()).orElseThrow(() -> new ResourceNotFoundException("stok not found"));
            double sisa = stokGudang.getQty() - e.getQty();
            if (sisa < 0) {
                throw new ResourceInfoException("stok tidak cukup");
            }
            stokGudang.setQty(sisa);
            stokGudangRepo.save(stokGudang);
        });


    }

    @Override
    @Transactional
    public void batalVerifikasi(int id) {
        SuratJalanH suratJalanH = suratJalanHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid sj id"));
        if (suratJalanH.getTerpakai()) {
            throw  new ResourceInfoException("Data sudah di pakai invoice. Verifikasi tidak bisa di batalkan");
        }
        if (!suratJalanH.getVerifikasi()) {
            throw new ResourceInfoException("Verifikasi sudah di batalkan");
        }
        suratJalanH.setVerifikasi(Boolean.FALSE);
        suratJalanH.setVerifikasiBy(0);
        suratJalanH.setVerifikasiAt(null);
        suratJalanHRepo.save(suratJalanH);

    }
}

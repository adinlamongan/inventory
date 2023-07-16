package com.adn.inventory.services.impl;

import com.adn.inventory.dto.*;
import com.adn.inventory.exceptions.ResourceInfoException;
import com.adn.inventory.exceptions.ResourceNotFoundException;
import com.adn.inventory.models.*;
import com.adn.inventory.repository.*;
import com.adn.inventory.security.CustomUser;
import com.adn.inventory.services.PindahBarangService;
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
public class PindahBarangServiceImpl implements PindahBarangService {

    private PindahBarangHRepo pindahBarangHRepo;

    private PindahBarangDRepo pindahBarangDRepo;

    private PindahBarangQueryRepo pindahBarangQueryRepo;

    private GudangRepo gudangRepo;

    private NomorOtomatis nomorOtomatis;

    private StokRepo stokRepo;

    private StokGudangRepo stokGudangRepo;

    @Override
    public DatatabelOutput<PindahBarangHQueryResponseDTO> getListPindahBarang(PagingRequest pagingRequest) {
        int pageSize = pagingRequest.getLength();
        int pageNumber = pagingRequest.getStart() == 0 ? 0 : pagingRequest.getStart() / pageSize;

        String keyword = pagingRequest.getSearch().getValue();

        Order order = pagingRequest.getOrder().get(0);
        int columnIndex = order.getColumn();
        String sortBy = pagingRequest.getColumns().get(columnIndex).getData();
        String direction = order.getDir().name();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<PindahBarangHQueryResponseDTO> pindahBarang = pindahBarangQueryRepo.findPindahBarang(keyword, sortBy, direction,
                pageable);

        List<PindahBarangHQueryResponseDTO> pindahBarangList = pindahBarang.getContent();
        DatatabelOutput<PindahBarangHQueryResponseDTO> result = new DatatabelOutput<>();
        result.setData(pindahBarangList);
        result.setDraw(pagingRequest.getDraw());
        result.setRecordsTotal(pindahBarangQueryRepo.getTotalRecord());
        result.setRecordsFiltered(pindahBarang.getTotalElements());
        return result;
    }

    @Override
    public PindahBarangHQueryResponseDTO getPindahBarangH(int id) {
        return pindahBarangHRepo.findHeaderById(id);
    }

    @Override
    public List<PindahBarangDQueryResponseDTO> getPindahBarangD(int pindahBarangId) {
        return pindahBarangDRepo.findDetailByPindahBarangId(pindahBarangId);
    }

    @Override
    @Transactional
    public void addPindahBarang(PindahBarangRequestDTO dto) {
        PindahBarangH pindahBarangH = new PindahBarangH();
        String nomor = nomorOtomatis.getNomor(dto.getTanggal(), "pindah_barangh");
        pindahBarangH.setNomor(nomor);
        pindahBarangH.setTanggal(dto.getTanggal());
        pindahBarangH.setGudangAsalId(dto.getGudangAsalId());
        pindahBarangH.setGudangTujuanId(dto.getGudangTujuanId());
        pindahBarangH.setKeterangan(dto.getKeterangan());
        pindahBarangHRepo.save(pindahBarangH);
        int length = dto.getProdukId().size();
        for (int i = 0; i < length; i++) {
            PindahBarangD pindahBarangD = new PindahBarangD();
            pindahBarangD.setPindahBarangId(pindahBarangH.getId());
            pindahBarangD.setStokId(dto.getStokId().get(i));
            pindahBarangD.setProdukId(dto.getProdukId().get(i));
            pindahBarangD.setQty(dto.getQty().get(i));
            pindahBarangDRepo.save(pindahBarangD);
//            StokGudang stokGudang = stokGudangRepo.findAndLockById(pindahBarangD.getStokId()).orElseThrow(() -> new ResourceNotFoundException("invalid stok id"));
//            double qty = stokGudang.getQty() - pindahBarangD.getQty();
//            if (qty < 0) {
//                throw new RuntimeException(pindahBarangD.getStokId() + " stok tidak cukup");
//            }
//            stokGudang.setQty(qty);
//            stokGudangRepo.save(stokGudang);
//
//            stokGudang = stokGudangRepo.findAndLockByProodukIdAndGudangId(pindahBarangD.getProdukId(), pindahBarangH.getGudangTujuanId());
//            if (stokGudang == null) {
//                StokGudang insert = new StokGudang();
//                insert.setGudangId(pindahBarangH.getGudangTujuanId());
//                insert.setProdukId(pindahBarangD.getProdukId());
//                insert.setQty(pindahBarangD.getQty());
//                stokGudangRepo.save(insert);
//            }else{
//                qty = stokGudang.getQty() + pindahBarangD.getQty();
//                stokGudang.setQty(qty);
//                stokGudangRepo.save(stokGudang);
//            }

        }


    }

    @Override
    @Transactional
    public void editPindahBarang(int id, PindahBarangRequestDTO dto) {
        PindahBarangH pindahBarangH = pindahBarangHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid id"));
        int oldGudangAsalid = pindahBarangH.getGudangAsalId();
        int oldGudangTujuanid = pindahBarangH.getGudangTujuanId();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dto.getTanggal());
        int dtoDate = calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH);
        calendar.setTime(pindahBarangH.getTanggal());
        int entTanggal = calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH);
        if (dtoDate != entTanggal) {
            String nomor = nomorOtomatis.getNomor(dto.getTanggal(), "pindah_barangh");
            pindahBarangH.setNomor(nomor);
        }
        pindahBarangH.setTanggal(dto.getTanggal());
        pindahBarangH.setGudangAsalId(dto.getGudangAsalId());
        pindahBarangH.setGudangTujuanId(dto.getGudangTujuanId());
        pindahBarangH.setKeterangan(dto.getKeterangan());
        pindahBarangHRepo.save(pindahBarangH);
//        List<PindahBarangD> detailOld = pindahBarangDRepo.findAllByPindahBarangId(id);
//        detailOld.forEach(e ->{
//            StokGudang stokGudang = stokGudangRepo.findAndLockByProodukIdAndGudangId(e.getProdukId(),oldGudangTujuanid);
//            double qty = stokGudang.getQty() - e.getQty();
//            if (qty < 0) {
//                throw new RuntimeException(stokGudang.getId() + " stok tidak cukup");
//            }
//            stokGudang.setQty(qty);
//            stokGudangRepo.save(stokGudang);
//            stokGudang = stokGudangRepo.findAndLockById(e.getStokId()).orElseThrow(() -> new ResourceNotFoundException(e.getStokId() + " invalid stok id`"));
//            stokGudang.setQty(stokGudang.getQty() + e.getQty());
//            stokGudangRepo.save(stokGudang);
//        });
        pindahBarangDRepo.deleteByPindahBarangId(id);
        int length = dto.getProdukId().size();
        for (int i = 0; i < length; i++) {
            PindahBarangD pindahBarangD = new PindahBarangD();
            pindahBarangD.setPindahBarangId(pindahBarangH.getId());
            pindahBarangD.setStokId(dto.getStokId().get(i));
            pindahBarangD.setProdukId(dto.getProdukId().get(i));
            pindahBarangD.setQty(dto.getQty().get(i));
            pindahBarangDRepo.save(pindahBarangD);
//            StokGudang stokGudang = stokGudangRepo.findAndLockById(pindahBarangD.getStokId()).orElseThrow(() -> new ResourceNotFoundException("invalid stok id"));
//            double qty = stokGudang.getQty() - pindahBarangD.getQty();
//            if (qty < 0) {
//                throw new RuntimeException(pindahBarangD.getStokId() + " stok tidak cukup");
//            }
//            stokGudang.setQty(qty);
//            stokGudangRepo.save(stokGudang);
//
//            stokGudang = stokGudangRepo.findAndLockByProodukIdAndGudangId(pindahBarangD.getProdukId(), pindahBarangH.getGudangTujuanId());
//            if (stokGudang == null) {
//                StokGudang insert = new StokGudang();
//                insert.setGudangId(pindahBarangH.getGudangTujuanId());
//                insert.setProdukId(pindahBarangD.getProdukId());
//                insert.setQty(pindahBarangD.getQty());
//                stokGudangRepo.save(insert);
//            }else{
//                qty = stokGudang.getQty() + pindahBarangD.getQty();
//                stokGudang.setQty(qty);
//                stokGudangRepo.save(stokGudang);
//            }
        }


    }

    @Override
    @Transactional
    public void deletePindahBarang(int id) {
        pindahBarangHRepo.deleteById(id);
        pindahBarangDRepo.deleteByPindahBarangId(id);

    }

    @Override
    public List<Gudang> getListGudang() {
        return (List<Gudang>) gudangRepo.findAll();
    }

    @Override
    public List<PindahBarangAutocompleteBarangByGudangAsalResponseDTO> getListBarangGudangAsal(String keyword, int gudangAsalId) {
        keyword = "%"+keyword+"%";
        return pindahBarangDRepo.findStokByKeywordAndGudangId(gudangAsalId, keyword);
    }

    @Override
    public List<PindahBarangAutocompleteBarangByGudangAsalResponseDTO> getListBarangGudangAsalEdit(String keyword, int gudangAsalId, int pindahBarangId) {
        keyword = "%"+keyword+"%";
        return pindahBarangDRepo.findStokByKeywordAndGudangIdForEdit(gudangAsalId, keyword, gudangAsalId);
    }

    @Override
    @Transactional
    public void verifikasi(int id) {
        PindahBarangH pindahBarangH = pindahBarangHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid id"));
        if (pindahBarangH.getVerifikasi()) {
            throw new ResourceInfoException("sudah terverifikasi");
        }
        List<PindahBarangD> detail = pindahBarangDRepo.findAllByPindahBarangId(id);
        if (detail.isEmpty()) {
            throw new ResourceNotFoundException("invalid pindah barang id");
        }
        pindahBarangH.setVerifikasi(Boolean.TRUE);
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        pindahBarangH.setVerifikasiBy(user.getUserId());
        pindahBarangH.setVerifikasiAt(LocalDateTime.now());
        pindahBarangHRepo.save(pindahBarangH);
        detail.forEach(e -> {
            StokGudang stokGudang = stokGudangRepo.findAndLockById(e.getStokId()).orElseThrow(() -> new ResourceNotFoundException("invalid stok id"));
            double qty = stokGudang.getQty() - e.getQty();
            if (qty < 0) {
                throw new ResourceInfoException(e.getStokId() + " stok tidak cukup");
            }
            stokGudang.setQty(qty);
            stokGudangRepo.save(stokGudang);

            stokGudang = stokGudangRepo.findAndLockByProodukIdAndGudangId(e.getProdukId(), pindahBarangH.getGudangTujuanId());
            if (stokGudang == null) {
                StokGudang insert = new StokGudang();
                insert.setGudangId(pindahBarangH.getGudangTujuanId());
                insert.setProdukId(e.getProdukId());
                insert.setQty(e.getQty());
                stokGudangRepo.save(insert);
            }else{
                qty = stokGudang.getQty() + e.getQty();
                stokGudang.setQty(qty);
                stokGudangRepo.save(stokGudang);
            }
        });


    }

    @Override
    @Transactional
    public void batalVerifikasi(int id) {
        PindahBarangH pindahBarangH = pindahBarangHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid id"));
        if (!pindahBarangH.getVerifikasi()) {
            throw new ResourceInfoException("Verifikasi sudah di batalkan");
        }

        List<PindahBarangD> detail = pindahBarangDRepo.findAllByPindahBarangId(id);
        if (detail.isEmpty()) {
            throw new ResourceNotFoundException("invalid pindah barang id");
        }

        detail.forEach(e -> {
            StokGudang stokGudang = stokGudangRepo.findAndLockById(e.getStokId()).orElseThrow(() -> new ResourceNotFoundException("invalid stok id"));
            double qty = stokGudang.getQty() + e.getQty();
            if (qty < 0) {
                throw new ResourceInfoException(e.getStokId() + " stok tidak cukup");
            }
            stokGudang.setQty(qty);
            stokGudangRepo.save(stokGudang);

            stokGudang = stokGudangRepo.findAndLockByProodukIdAndGudangId(e.getProdukId(), pindahBarangH.getGudangTujuanId());
            qty = stokGudang.getQty() - e.getQty();
            if (qty < 0) {
                throw new ResourceInfoException(stokGudang.getId() + " stok tidak cukup");
            }
            stokGudang.setQty(qty);
            stokGudangRepo.save(stokGudang);
        });
        pindahBarangH.setVerifikasiBy(0);
        pindahBarangH.setVerifikasiAt(null);
        pindahBarangH.setVerifikasi(Boolean.FALSE);
        pindahBarangHRepo.save(pindahBarangH);

    }
}

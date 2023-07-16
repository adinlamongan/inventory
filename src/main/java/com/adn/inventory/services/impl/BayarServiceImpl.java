package com.adn.inventory.services.impl;

import com.adn.inventory.dto.*;
import com.adn.inventory.exceptions.ResourceInfoException;
import com.adn.inventory.exceptions.ResourceNotFoundException;
import com.adn.inventory.models.*;
import com.adn.inventory.repository.BayarDRepo;
import com.adn.inventory.repository.BayarHRepo;
import com.adn.inventory.repository.BayarQueryRepo;
import com.adn.inventory.repository.JualHRepo;
import com.adn.inventory.security.CustomUser;
import com.adn.inventory.services.BayarService;
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
public class BayarServiceImpl implements BayarService {
    private NomorOtomatis nomorOtomatis;

    private JualHRepo jualHRepo;

    private BayarHRepo bayarHRepo;

    private BayarDRepo bayarDRepo;
    private BayarQueryRepo bayarQueryRepo;

    @Override
    public DatatabelOutput<BayarHQueryResponseDTO> getListBayar(PagingRequest pagingRequest) {
        int pageSize = pagingRequest.getLength();
        int pageNumber = pagingRequest.getStart() == 0 ? 0 : pagingRequest.getStart() / pageSize;

        String keyword = pagingRequest.getSearch().getValue();

        Order order = pagingRequest.getOrder().get(0);
        int columnIndex = order.getColumn();
        String sortBy = pagingRequest.getColumns().get(columnIndex).getData();
        String direction = order.getDir().name();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<BayarHQueryResponseDTO> bayar = bayarQueryRepo.findBayar(keyword, sortBy, direction,
                pageable);

        List<BayarHQueryResponseDTO> bayarList = bayar.getContent();
        DatatabelOutput<BayarHQueryResponseDTO> result = new DatatabelOutput<>();
        result.setData(bayarList);
        result.setDraw(pagingRequest.getDraw());
        result.setRecordsTotal(bayarQueryRepo.getTotalRecord());
        result.setRecordsFiltered(bayar.getTotalElements());
        return result;
    }

    @Override
    public BayarHQueryResponseDTO getBayarH(int id) {
        return bayarHRepo.findHeaderById(id);
    }

    @Override
    public List<BayarDQueryResponseDTO> getBayarD(int bayarId) {
        return bayarDRepo.findDetatailByBayarId(bayarId);
    }

    @Override
    @Transactional
    public void addJual(BayarRequestDTO dto) {
        BayarH bayarH = new BayarH();
        String nomor = nomorOtomatis.getNomor(dto.getTanggal(), "bayarh");
        bayarH.setNomor(nomor);
        bayarH.setTanggal(dto.getTanggal());
        bayarH.setCustomerId(dto.getCustomerId());
        bayarH.setKeterangan(dto.getKeterangan());
        bayarH.setJumlah(dto.getJumlah());
        bayarHRepo.save(bayarH);

        int length = dto.getJualId().size();
        for (int i = 0; i < length; i++) {
            if (dto.getJmlBayar().get(i) > 0){
                BayarD bayarD = new BayarD();
                bayarD.setBayarId(bayarH.getId());
                bayarD.setJualId(dto.getJualId().get(i));
                bayarD.setJmlBayar(dto.getJmlBayar().get(i));
                bayarD.setKeterangan(dto.getKeteranganDetail().get(i));
                bayarDRepo.save(bayarD);
            }

        }

    }

    @Override
    @Transactional
    public void editBayar(int id, BayarRequestDTO dto) {
        BayarH bayarH = bayarHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid id"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dto.getTanggal());
        int dtoDate = calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH);
        calendar.setTime(bayarH.getTanggal());
        int entTanggal = calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH);
        if (dtoDate != entTanggal) {
            String nomor = nomorOtomatis.getNomor(dto.getTanggal(), "lpbh");
            bayarH.setNomor(nomor);
        }
        bayarH.setTanggal(dto.getTanggal());
        bayarH.setCustomerId(dto.getCustomerId());
        bayarH.setKeterangan(dto.getKeterangan());
        bayarH.setJumlah(dto.getJumlah());
        bayarHRepo.save(bayarH);

        bayarDRepo.deleteByBayarId(bayarH.getId());

        int length = dto.getJualId().size();
        for (int i = 0; i < length; i++) {
            if (dto.getJmlBayar().get(i) > 0) {
                BayarD bayarD = new BayarD();
                bayarD.setBayarId(bayarH.getId());
                bayarD.setJualId(dto.getJualId().get(i));
                bayarD.setJmlBayar(dto.getJmlBayar().get(i));
                bayarD.setKeterangan(dto.getKeteranganDetail().get(i));
                bayarDRepo.save(bayarD);
            }
        }
    }

    @Override
    @Transactional
    public void deleteBayar(int id) {
        bayarDRepo.deleteByBayarId(id);
        bayarHRepo.deleteById(id);
    }

    @Override
    public List<BayarListInvoiceQueryResponseDTO> getListInvoice(int customerId, int bayarId) {
        return bayarId == 0 ? bayarDRepo.findJualHByCustomerId(customerId) : bayarDRepo.findJualHByCustomerIdForEdit(customerId, bayarId);
    }

    @Override
    @Transactional
    public void verifikasi(int id) {
        BayarH bayarH = bayarHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid id"));
        if (bayarH.getVerifikasi()) {
            throw new ResourceInfoException("sudah terverifikasi");
        }
        bayarH.setVerifikasi(true);
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bayarH.setVerifikasiBy(user.getUserId());
        bayarH.setVerifikasiAt(LocalDateTime.now());
        bayarHRepo.save(bayarH);
        List<BayarD> bayarDList = bayarDRepo.findAllByJualId(id);
        if (bayarDList == null) {
            throw new ResourceNotFoundException("invalid bayar id");
        }
        bayarDList.forEach(e ->{
            JualH jualH = jualHRepo.findById(e.getJualId()).orElseThrow(() -> new ResourceNotFoundException("invalid jual id"));
            double totalBayar = jualH.getBayar() + e.getJmlBayar();
            jualH.setBayar(totalBayar);
            if (totalBayar > jualH.getTotal()) {
                jualH.setLunas(true);
            }
            jualHRepo.save(jualH);
        });

    }

    @Override
    @Transactional
    public void batalVerifikasi(int id) {
        BayarH bayarH = bayarHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid id"));
        if (bayarH.getVerifikasi()) {
            throw new ResourceInfoException("sudah batal verifikasi");
        }
        bayarH.setVerifikasi(false);
        bayarH.setVerifikasiBy(0);
        bayarH.setVerifikasiAt(null);
        bayarHRepo.save(bayarH);
        List<BayarD> bayarDList = bayarDRepo.findAllByJualId(id);
        if (bayarDList == null) {
            throw new ResourceNotFoundException("invalid bayar id");
        }
        bayarDList.forEach(e ->{
            JualH jualH = jualHRepo.findById(e.getJualId()).orElseThrow(() -> new ResourceNotFoundException("invalid jual id"));
            double totalBayar = jualH.getBayar() - e.getJmlBayar();
            if (totalBayar < 0) {
                throw  new RuntimeException("terjadi kesalahan!!");
            }
            jualH.setBayar(totalBayar);
            jualH.setLunas(false);
            jualHRepo.save(jualH);
        });

    }
}

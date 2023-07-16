package com.adn.inventory.services.impl;

import com.adn.inventory.dto.*;
import com.adn.inventory.exceptions.ResourceInfoException;
import com.adn.inventory.exceptions.ResourceNotFoundException;
import com.adn.inventory.models.*;
import com.adn.inventory.repository.*;
import com.adn.inventory.security.CustomUser;
import com.adn.inventory.services.JualService;
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
public class JualServiceImpl implements JualService {


    private SuratJalanHRepo suratJalanHRepo;

    private NomorOtomatis nomorOtomatis;

    private JualHRepo jualHRepo;

    private JualDRepo jualDRepo;

    private JualQueryRepo jualQueryRepo;


    @Override
    public DatatabelOutput<JualHQueryResponseDTO> getListJual(PagingRequest pagingRequest) {
        int pageSize = pagingRequest.getLength();
        int pageNumber = pagingRequest.getStart() == 0 ? 0 : pagingRequest.getStart() / pageSize;

        String keyword = pagingRequest.getSearch().getValue();

        Order order = pagingRequest.getOrder().get(0);
        int columnIndex = order.getColumn();
        String sortBy = pagingRequest.getColumns().get(columnIndex).getData();
        String direction = order.getDir().name();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<JualHQueryResponseDTO> jual = jualQueryRepo.findJual(keyword, sortBy, direction,
                pageable);

        List<JualHQueryResponseDTO> jualList = jual.getContent();
        DatatabelOutput<JualHQueryResponseDTO> result = new DatatabelOutput<>();
        result.setData(jualList);
        result.setDraw(pagingRequest.getDraw());
        result.setRecordsTotal(jualQueryRepo.getTotalRecord());
        result.setRecordsFiltered(jual.getTotalElements());
        return result;
    }

    @Override
    public JualHQueryResponseDTO getJualH(int id) {
        return jualHRepo.findHeaderById(id);
    }

    @Override
    public List<JualDQueryResponseDTO> getJualD(int jualId) {
        return jualDRepo.findDetailByJualId(jualId);
    }

    @Override
    @Transactional
    public void addJual(JualRequestDTO dto) {
        JualH jualH = new JualH();
        String nomor = nomorOtomatis.getNomor(dto.getTanggal(), "jualh");
        jualH.setNomor(nomor);
        jualH.setTanggal(dto.getTanggal());
        jualH.setSuratJalanId(dto.getSuratJalanId());
        jualH.setCustomerId(dto.getCustomerId());
        jualH.setKeterangan(dto.getKeterangan());
        jualH.setSubTotal(dto.getSubTotal());
        jualH.setPpn(dto.getPpn());
        jualH.setPpnNominal(dto.getPpnNominal());
        jualH.setDiskon(dto.getDiskon());
        jualH.setTotal(dto.getTotal());
        jualHRepo.save(jualH);

        int length = dto.getProdukId().size();
        for (int i = 0; i < length; i++) {
            JualD jualD = new JualD();
            jualD.setJualId(jualH.getId());
            jualD.setProdukId(dto.getProdukId().get(i));
            jualD.setQty(dto.getQty().get(i));
            jualD.setHarga(dto.getHarga().get(i));
            jualD.setTotal(dto.getTotalHarga().get(i));
            jualDRepo.save(jualD);
        }
        SuratJalanH suratJalanH = suratJalanHRepo.findById(jualH.getSuratJalanId()).orElseThrow(() -> new ResourceNotFoundException("invalid sj id"));
        suratJalanH.setTerpakai(true);
        suratJalanHRepo.save(suratJalanH);

    }

    @Override
    @Transactional
    public void editJual(int id, JualRequestDTO dto) {
        JualH jualH = jualHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid id"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dto.getTanggal());
        int dtoDate = calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH);
        calendar.setTime(jualH.getTanggal());
        int entTanggal = calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH);
        if (dtoDate != entTanggal) {
            String nomor = nomorOtomatis.getNomor(dto.getTanggal(), "lpbh");
            jualH.setNomor(nomor);
        }
        jualH.setTanggal(dto.getTanggal());
        jualH.setKeterangan(dto.getKeterangan());
        jualH.setSubTotal(dto.getSubTotal());
        jualH.setPpn(dto.getPpn());
        jualH.setPpnNominal(dto.getPpnNominal());
        jualH.setDiskon(dto.getDiskon());
        jualHRepo.save(jualH);

        jualDRepo.deleteByJualId(jualH.getId());

        int length = dto.getProdukId().size();
        for (int i = 0; i < length; i++) {
            JualD jualD = new JualD();
            jualD.setJualId(jualH.getId());
            jualD.setProdukId(dto.getProdukId().get(i));
            jualD.setQty(dto.getQty().get(i));
            jualD.setHarga(dto.getHarga().get(i));
            jualD.setTotal(dto.getTotalHarga().get(i));
            jualDRepo.save(jualD);
        }

    }

    @Override
    @Transactional
    public void deleteJual(int id, int suratJalanId) {
        jualDRepo.deleteByJualId(id);
        jualHRepo.deleteById(id);
        SuratJalanH suratJalanH = suratJalanHRepo.findById(suratJalanId).orElseThrow(() -> new ResourceNotFoundException("invalid sj id"));
        suratJalanH.setTerpakai(false);
        suratJalanHRepo.save(suratJalanH);

    }

    @Override
    public List<JualListSJQueryResponseDTO> getListSJ(String keyword) {
        return jualHRepo.findSuratJalanByLike("%"+keyword+"%");
    }

    @Override
    public List<JualListProdukSJResponseDTO> getListBarangSJ(int suratJalanId) {
        return jualDRepo.findSuratJalanDBySuratJalanId(suratJalanId);
    }

    @Override
    @Transactional
    public void verifikasi(int id) {
        JualH jualH = jualHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid id"));
        if (jualH.getVerifikasi()) {
            throw new ResourceInfoException("sudah terverifikasi");
        }
        jualH.setVerifikasi(Boolean.TRUE);
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        jualH.setVerifikasiBy(user.getUserId());
        jualH.setVerifikasiAt(LocalDateTime.now());
        jualHRepo.save(jualH);
    }

    @Override
    @Transactional
    public void batalVerifikasi(int id) {
        JualH jualH = jualHRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid id"));
        if (!jualH.getVerifikasi()) {
            throw new ResourceInfoException("Verifikasi sudah di batalkan");
        }
        if (jualH.getBayar() > 0) {
            throw new ResourceInfoException("Sudah ada pembayaran. Batal verifikasi tidak bisa di lakukan");
        }
        jualH.setVerifikasiBy(0);
        jualH.setVerifikasiAt(null);
        jualH.setVerifikasi(Boolean.FALSE);
        jualHRepo.save(jualH);
    }
}

package com.adn.inventory.services.impl;

import com.adn.inventory.dto.KartuStokRequestDTO;
import com.adn.inventory.dto.KartuStokResponseDTO;
import com.adn.inventory.models.KartuStokQuery;
import com.adn.inventory.models.KartuStokQuerySaldoAwal;
import com.adn.inventory.repository.KartuStokRepo;
import com.adn.inventory.services.KartuStokService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class KartuStokServiceImpl implements KartuStokService {

    private KartuStokRepo kartuStokRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<KartuStokResponseDTO> getData(KartuStokRequestDTO dto) {

        List<KartuStokQuerySaldoAwal> listQtyAwal = kartuStokRepo.getQtyAwal(dto);

        Map<Integer,List<KartuStokQuery>> data = kartuStokRepo.getData(dto).stream().collect(Collectors.groupingBy(
                KartuStokQuery::getGudangId,
                Collectors.toList()
        ));




        List<KartuStokResponseDTO> dtoList = listQtyAwal.stream().map(e -> {
            KartuStokResponseDTO  dto1 = new KartuStokResponseDTO();
            dto1.setGudangId(e.getGudangId());
            dto1.setNamaGudang(e.getNamaGudang());
            dto1.setQtyAwal(e.getQtyAwal());

            List<KartuStokQuery> details = new ArrayList<>();
            data.forEach((k,v) -> {
                if (k == e.getGudangId()) {
                    dto1.setDetail(v);
                }
            });

            return dto1;
        }).collect(Collectors.toList());

//        List<KartuStokResponseDTO> dtoList = data.values().stream().map( e ->{
//            KartuStokResponseDTO header = new KartuStokResponseDTO();
//            header.setGudangId(e.get(0).getGudangId());
//            header.setNamaGudang(e.get(0).getNamaGudang());
//
//            for(KartuStokQuerySaldoAwal p : listQtyAwal){
//                if(p.getGudangId() ==e.get(0).getGudangId()){
//                    header.setQtyAwal(p.getQtyAwal());
//                }
//            }
//
//            List<KartuStokQuery> details = e.stream().map((ee) -> {
//                KartuStokQuery detail = new KartuStokQuery();
//                detail = modelMapper.map(ee,KartuStokQuery.class);
//                return  detail;
//            }).collect(Collectors.toList());
//            header.setDetail(details);
//            return header;
//        }).collect(Collectors.toList());
        return dtoList;
    }

    
}

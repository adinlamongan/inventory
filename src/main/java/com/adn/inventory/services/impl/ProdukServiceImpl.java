package com.adn.inventory.services.impl;

import com.adn.inventory.dto.ProdukResponseDTO;
import com.adn.inventory.repository.BarangQueryRepo;
import com.adn.inventory.repository.BarangRepo;
import com.adn.inventory.services.ProdukService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProdukServiceImpl implements ProdukService {

    private BarangQueryRepo produkQueryRepo;

    private BarangRepo produkRepo;

    @Override
    public List<ProdukResponseDTO> getListBarangAutocomplete(String keyword) {
        String key = "%"+keyword+"%";
        return produkRepo.listBarangAutoComplete(key);
    }
}

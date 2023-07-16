package com.adn.inventory.services;

import com.adn.inventory.dto.ProdukResponseDTO;

import java.util.List;

public interface ProdukService {
    List<ProdukResponseDTO> getListBarangAutocomplete(String keyword);
}

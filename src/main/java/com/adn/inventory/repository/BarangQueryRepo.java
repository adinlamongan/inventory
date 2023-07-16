package com.adn.inventory.repository;


import com.adn.inventory.dto.ProdukResponseDTO;

import java.util.List;

public interface BarangQueryRepo {

    List<ProdukResponseDTO> listBarangAutoComplete(String keyword);
}

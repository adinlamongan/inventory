package com.adn.inventory.services;

import com.adn.inventory.dto.KartuStokRequestDTO;
import com.adn.inventory.dto.KartuStokResponseDTO;

import java.util.List;

public interface KartuStokService {

    List<KartuStokResponseDTO> getData(KartuStokRequestDTO dto);
    
}

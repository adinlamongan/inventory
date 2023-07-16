package com.adn.inventory.services;

import com.adn.inventory.dto.KartuStokRequestDTO;
import com.adn.inventory.dto.KartuStokResponseDTO;
import com.adn.inventory.dto.UmurPiutangQueryResponseDTO;

import java.util.List;

public interface UmurPiutangService {

    List<UmurPiutangQueryResponseDTO> getData(int customerId);
    
}

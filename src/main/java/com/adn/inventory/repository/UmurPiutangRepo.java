package com.adn.inventory.repository;

import com.adn.inventory.dto.KartuStokRequestDTO;
import com.adn.inventory.dto.UmurPiutangQueryResponseDTO;
import com.adn.inventory.models.KartuStokQuery;
import com.adn.inventory.models.KartuStokQuerySaldoAwal;

import java.util.List;

public interface UmurPiutangRepo {
    List<UmurPiutangQueryResponseDTO> getData(int customerId);
}

package com.adn.inventory.services.impl;

import com.adn.inventory.dto.UmurPiutangQueryResponseDTO;
import com.adn.inventory.repository.UmurPiutangRepo;
import com.adn.inventory.services.UmurPiutangService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UmurPiutangServiceImpl implements UmurPiutangService {
    private UmurPiutangRepo umurPiutangRepo;

    @Override
    public List<UmurPiutangQueryResponseDTO> getData(int customerId) {
        return umurPiutangRepo.getData(customerId);
    }
}

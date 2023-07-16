package com.adn.inventory.dto;

import com.adn.inventory.models.Gudang;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SuratJalanListSODResponseDTO {
    List<SODQueryResponseDTO> listProdukSO;

    //List<StokGudangResponseDTO> gudangs;
    List<Gudang> gudangs;
}

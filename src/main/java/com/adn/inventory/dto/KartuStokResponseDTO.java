package com.adn.inventory.dto;

import com.adn.inventory.models.KartuStokQuery;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KartuStokResponseDTO {
    private int gudangId;
    private String namaGudang;

    private double qtyAwal;
    private List<KartuStokQuery> detail;
}

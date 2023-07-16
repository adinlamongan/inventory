package com.adn.inventory.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LpbListPOHQueryResponseDTO {
    private int id;

    private String nomor;

    private Date tanggal;

    private int supplierId;
    
    private String namaSupplier;

}

package com.adn.inventory.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PODQueryResponseDTO {
    private Integer id;

    private int purchaseOrderId;

    private int produkId;

    private String kodeProduk;

    private String namaProduk;
    private String namaSatuan;

    private double harga;

    private double qty;

    private double total;
}

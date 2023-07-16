package com.adn.inventory.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class JualListProdukSJResponseDTO {
    private Integer id;
    private int produkId;
    private String kodeProduk;
    private String namaProduk;
    private double qty;
    private double harga;
    private String namaSatuan;
}

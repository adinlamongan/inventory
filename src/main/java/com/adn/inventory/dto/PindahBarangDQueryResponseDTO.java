package com.adn.inventory.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PindahBarangDQueryResponseDTO {
    private int id;

    private int pindahProdukId;


    private int produkId;

    private int stokId;

    private double qty;

    private String kodeProduk;

    private String namaProduk;

    private String namaSatuan;

}

package com.adn.inventory.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PindahBarangAutocompleteBarangByGudangAsalResponseDTO {

    private Integer id;

    private int produkId;

    private String kode;

    private String nama;

    private String namaSatuan;
    private String namaKategori;

    private double qty;
    private double sisa;

}

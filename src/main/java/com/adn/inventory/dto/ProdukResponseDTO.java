package com.adn.inventory.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProdukResponseDTO {
    private Integer id;

    private String kode;

    private String nama;

    private int kategori_id;

    private String nama_kategori;

    private int satuan_id;

    private String nama_satuan;

    private BigDecimal qty;
}

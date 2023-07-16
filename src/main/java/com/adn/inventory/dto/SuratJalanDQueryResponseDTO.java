package com.adn.inventory.dto;

import lombok.Data;

@Data
public class SuratJalanDQueryResponseDTO {
    private Integer id;

    private int suratJalanId;

    private int salesOrderDetailId;

    private int produkId;
    private int gudangId;
    private int stokId;

    private double qty;

    private String kodeProduk;

    private String namaProduk;
    private String namaSatuan;

    private String namaGudang;

    private double qtySo;
    private double qtyStok;
}

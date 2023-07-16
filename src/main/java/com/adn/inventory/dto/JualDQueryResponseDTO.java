package com.adn.inventory.dto;

import lombok.Data;

@Data
public class JualDQueryResponseDTO {
    private Integer id;

    private int jualId;

    private int produkId;

    private double qty;
    private double harga;
    private double total;

    private String kodeProduk;

    private String namaProduk;
    private String namaSatuan;


}

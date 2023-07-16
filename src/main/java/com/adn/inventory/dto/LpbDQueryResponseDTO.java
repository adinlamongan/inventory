package com.adn.inventory.dto;

import lombok.Data;

@Data
public class LpbDQueryResponseDTO {
    private Integer id;

    private int lpbId;

    private int purchaseOrderDetailId;

    private int produkId;
    private int gudangId;

    private double qty;

    private String kodeProduk;

    private String namaProduk;
    private String namaSatuan;
    private String namaGudang;

    private double qtyPo;
}

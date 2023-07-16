package com.adn.inventory.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BayarDQueryResponseDTO {
    private Integer id;

    private int jualId;
    private String nomorInvoice;

    private Date tanggal;

    private double nilaiInvoice;
    private double jmlTagihan;
    private double jmlBayar;
    private String keterangan;

}

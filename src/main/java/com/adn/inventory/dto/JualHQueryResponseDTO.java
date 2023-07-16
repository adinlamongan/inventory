package com.adn.inventory.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class JualHQueryResponseDTO {

    private int id;

    private String nomor;

    private Date tanggal;

    private int suratJalanId;
    private String keterangan;
    private double subTotal;
    private double ppn;
    private double ppnNominal;
    private double diskon;
    private double total;

    private Boolean verifikasi;
    private Boolean lunas;
    private String nomorSo;
    private String nomorSj;
    private int customerId;
    private String namaCustomer;

}

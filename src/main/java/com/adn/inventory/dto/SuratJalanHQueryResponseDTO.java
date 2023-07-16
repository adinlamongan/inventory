package com.adn.inventory.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SuratJalanHQueryResponseDTO {

    private int id;

    private String nomor;

    private Date tanggal;

    private int salesOrderId;
    private String keterangan;
    private Boolean verifikasi;
    private String nomorSo;
    private int customerId;
    private String namaCustomer;
}

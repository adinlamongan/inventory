package com.adn.inventory.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SOHQueryResponseDTO {
    private int id;

    private String nomor;

    private Date tanggal;

    private int customerId;

    private String namaCustomer;

    private String keterangan;

    private double total;

    private Boolean verifikasi;
}

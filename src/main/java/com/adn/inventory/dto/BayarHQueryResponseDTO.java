package com.adn.inventory.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BayarHQueryResponseDTO {

    private int id;

    private String nomor;

    private Date tanggal;

    private String keterangan;
    private double jumlah;
    private Boolean verifikasi;
    private int customerId;
    private String namaCustomer;

}

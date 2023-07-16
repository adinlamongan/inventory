package com.adn.inventory.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class POHQueryResponseDTO {
    private int id;

    private String nomor;

    private Date tanggal;

    private int supplierId;
    
    private String namaSupplier;

    private String keterangan;

    private double total;

    private Boolean verifikasi;
}

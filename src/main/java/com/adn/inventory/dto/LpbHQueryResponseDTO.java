package com.adn.inventory.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class LpbHQueryResponseDTO {

    private int id;

    private String nomor;

    private Date tanggal;

    private int purchaseOrderId;
    private String keterangan;
    private Boolean verifikasi;
    private String nomorPo;
    private int supplierId;
    private String namaSupplier;
}

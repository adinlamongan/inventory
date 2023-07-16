package com.adn.inventory.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Table(name = "purchase_orderh")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderH extends AuditEntity {


    @Id
    private Integer id;

    private String nomor;

    private Date tanggal;

    private int supplierId;

    private String keterangan;

    private double total;

    private Boolean verifikasi = false;

    private Boolean habis = false;

    private int verifikasiBy;
    private LocalDateTime verifikasiAt;

}

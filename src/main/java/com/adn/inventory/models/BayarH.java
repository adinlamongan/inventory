package com.adn.inventory.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Table(name = "bayarh")
@AllArgsConstructor
@NoArgsConstructor
public class BayarH extends AuditEntity {
    @Id
    private int id;

    private String nomor;

    private Date tanggal;

    private int customerId;
    private String keterangan;

    private double jumlah;
    private Boolean verifikasi = false;
    private int verifikasiBy;
    private LocalDateTime verifikasiAt;
}

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

@Data
@Table(name = "jualh")
@AllArgsConstructor
@NoArgsConstructor
public class JualH  extends AuditEntity {
    @Id
    private int id;

    private String nomor;

    private Date tanggal;

    private int suratJalanId;

    private String keterangan;

    private Boolean verifikasi = false;

    private int customerId;

    private double subTotal;
    private double ppn;
    private double ppnNominal;
    private double diskon;
    private double total;
    private Boolean lunas = false;
    private double bayar;
    private int verifikasiBy;
    private LocalDateTime verifikasiAt;

}

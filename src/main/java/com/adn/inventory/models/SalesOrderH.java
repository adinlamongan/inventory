package com.adn.inventory.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Table(name = "sales_orderh")
@NoArgsConstructor
public class SalesOrderH extends AuditEntity {
    @Id
    private Integer id;

    private String nomor;

    private Date tanggal;

    private int customerId;

    private String keterangan;

    private double total;

    private Boolean verifikasi = false;
    private Boolean terpakai = false;

    private int verifikasiBy;
    private LocalDateTime verifikasiAt;

}

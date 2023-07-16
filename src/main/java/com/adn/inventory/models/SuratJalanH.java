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
@Table(name = "surat_jalanh")
@NoArgsConstructor
public class SuratJalanH extends AuditEntity  {
    @Id
    private int id;

    private String nomor;

    private Date tanggal;

    private int salesOrderId;

    private String keterangan;

    private Boolean verifikasi = false;

    private int customerId;

    private Boolean terpakai = false;

    private int verifikasiBy;

    private LocalDateTime verifikasiAt;
//
//    @Version
//    private int version;
//
//    @CreatedDate
//    private LocalDateTime createdAt;
//
//    @CreatedBy
//    private int createdBy;
//
//    @LastModifiedDate
//    private LocalDateTime updatedAt;
//
//    @LastModifiedBy
//    private int updatedBy;

}

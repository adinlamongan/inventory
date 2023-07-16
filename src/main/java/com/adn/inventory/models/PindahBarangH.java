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

@Table(name = "pindah_barangh")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PindahBarangH extends AuditEntity {

    @Id
    private Integer id;

    private String nomor;

    private Date tanggal;

    @Column("gudang_asal_id")
    private int gudangAsalId;

    @Column("gudang_tujuan_id")
    private int gudangTujuanId;

    private String keterangan;

    private Boolean verifikasi = false;

    private int verifikasiBy;
    private LocalDateTime verifikasiAt;

}

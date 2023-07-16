package com.adn.inventory.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Table(name = "lpbh")
@AllArgsConstructor
@NoArgsConstructor
public class LpbH  extends AuditEntity {


    @Id
    private Integer id;

    private String nomor;

    private Date tanggal;

    private int purchaseOrderId;

    private String keterangan;

    private Boolean verifikasi = false;

    private int verifikasiBy;
    private LocalDateTime verifikasiAt;

}

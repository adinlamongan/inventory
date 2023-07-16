package com.adn.inventory.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KartuStokQuerySaldoAwal {
    @Column("gudang_id")
    private int gudangId;

    @Column("nama_gudang")
    private String namaGudang;

    @Column("qty_awal")
    private double qtyAwal;
}

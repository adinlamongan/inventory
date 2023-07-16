package com.adn.inventory.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Table(name = "pindah_barangd")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PindahBarangD implements Serializable {

    @Id
    private Integer id;

    @Column("pindah_barang_id")
    private Integer pindahBarangId;

    @Column("stok_id")
    private int stokId;

    @Column("produk_id")
    private int produkId;

    private double qty;

}

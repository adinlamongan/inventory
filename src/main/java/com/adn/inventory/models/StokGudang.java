package com.adn.inventory.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("stok_gudang")
public class StokGudang {
    @Id
    private int id;

    @Column("produk_id")
    private int produkId;

    @Column("gudang_id")
    private int gudangId;
    private double qty;

}

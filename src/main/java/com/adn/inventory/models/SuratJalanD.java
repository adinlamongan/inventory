package com.adn.inventory.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@Table(name = "surat_jaland")
@AllArgsConstructor
@NoArgsConstructor
public class SuratJalanD implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5011462319421803479L;


    @Id
    private Integer id;

    @Column("surat_jalan_id")
    private int suratJalanId;

    @Column("sales_order_detail_id")
    private int salesOrderDId;

    @Column("produk_id")
    private int produkId;

    @Column("gudang_id")
    private int gudangId;

    @Column("stok_id")
    private int stokId;


    private double qty;
}

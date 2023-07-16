package com.adn.inventory.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@Table(name = "lpbd")
@AllArgsConstructor
@NoArgsConstructor
public class LpbD implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5011462319421803479L;


    @Id
    private Integer id;

    @Column("lpb_id")
    private int lpbId;

    @Column("purchase_order_detail_id")
    private int purchaseOrderDId;

    @Column("produk_id")
    private int produkId;

    @Column("gudang_id")
    private int gudangId;


    private double qty;
}

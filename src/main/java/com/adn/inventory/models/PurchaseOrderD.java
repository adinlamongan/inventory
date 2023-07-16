package com.adn.inventory.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_orderd")
public class PurchaseOrderD implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -8694579429822777613L;

    @Id
    private int id;

    @Column("purchase_order_id")
    private int purchaseOrderId;

    @Column("produk_id")
    private int produkId;

    private double harga;

    private double qty;
    @Column("qty_terima")
    private double qtyTerima;

    private double total;
}

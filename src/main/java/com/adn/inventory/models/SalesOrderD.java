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
@Table(name = "sales_orderd")
public class SalesOrderD implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -8694579429822777613L;

    @Id
    private int id;

    private int salesOrderId;

    private int produkId;

    private double harga;

    private double qty;


    private double total;
}

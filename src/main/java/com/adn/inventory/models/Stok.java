package com.adn.inventory.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;

@Table(name = "stok")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stok implements Serializable {

    @Id
    private int id;

    @Column("produk_id")
    private int produkId;

    @Column("gudang_id")
    private int gudangId;

    @Column("reference_id")
    private int rerenceId;

    @Column("table_name")
    private String tableName;

    @Column("reference_detail_id")
    private int rerenceDetailId;

    @Column("table_name_detail")
    private String tableNameDetail;

    private double qty;

    private double terpakai;

    @Column("created_at")
    private Date cratedAt = new Date();

    @Column("created_by")
    private int createdBy = 0;

}

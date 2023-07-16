package com.adn.inventory.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bayard")
public class BayarD {

    @Id
    private int id;

    @Column("bayar_id")
    private int bayarId;
    @Column("jual_id")
    private int jualId;

    @Column("jml_bayar")
    private double jmlBayar;

    private String keterangan;
}

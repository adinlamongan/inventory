package com.adn.inventory.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "master_produk")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produk implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6618018876824250694L;

    @Id
    private Integer id;

    private String kode;

    private String nama;

    @Column("kategori_id")
    private int kategoriId;

    private BigDecimal qty;

}

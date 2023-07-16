package com.adn.inventory.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "master_customer")
public class Customer {

    @Id
    private Integer id;

    private String kode;

    private String nama;

    private String alamat;

    private String telepon;

    private String fax;

}

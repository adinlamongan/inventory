package com.adn.inventory.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Table(name = "master_gudang")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gudang implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8508130888541708063L;

    @Id
    int id;

    String nama;

}

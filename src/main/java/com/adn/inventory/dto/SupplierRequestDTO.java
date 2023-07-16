package com.adn.inventory.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class SupplierRequestDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4005555528146928572L;

    private String kode;

    private String nama;

    private String alamat;

    private String telepon;

    private String fax;
}

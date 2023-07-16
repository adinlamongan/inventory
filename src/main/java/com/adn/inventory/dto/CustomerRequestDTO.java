package com.adn.inventory.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CustomerRequestDTO {
    private Integer id;

    private String kode;

    private String nama;

    private String alamat;

    private String telepon;

    private String fax;
}

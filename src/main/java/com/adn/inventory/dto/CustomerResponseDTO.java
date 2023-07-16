package com.adn.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CustomerResponseDTO {
    private int id;
    private String kode;
    private String nama;
    private String alamat;
}

package com.adn.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UmurPiutangQueryResponseDTO {
    private int customer_id;
    private String nama_customer;
    private double ar_30;
    private double ar_31_60;
    private double ar_61_90;
    private double ar_90_lebih;

}

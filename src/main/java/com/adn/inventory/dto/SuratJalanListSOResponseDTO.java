package com.adn.inventory.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Date;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SuratJalanListSOResponseDTO {
    private int id;

    private String nomor;

    private Date tanggal;

    private int customerId;

    private String namaCustomer;

}

package com.adn.inventory.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BayarRequestDTO {

    private int id;
    private String nomor;
    private Date tanggal;
    private int customerId;
    private String keterangan;
    private double jumlah;
    private List<Integer> jualId;
    private List<Double> jmlBayar;
    private List<String> keteranganDetail;
}

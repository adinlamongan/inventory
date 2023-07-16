package com.adn.inventory.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PORequestDTO implements Serializable {
    

    /**
     *
     */
    private static final long serialVersionUID = -5156641144396299535L;

    private int id;
    private String nomor;
    private Date tanggal;
    private int supplierId;
    private double diskon;
    private double ppn;
    private String keterangan;
    private double total;
    private List<Integer> produkId;
    private List<Double> harga;
    private List<Double> qty;
    private List<Double> totalDetail;
}

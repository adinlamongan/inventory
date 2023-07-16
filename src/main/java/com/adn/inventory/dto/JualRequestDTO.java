package com.adn.inventory.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class JualRequestDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3583155541343721326L;
    
    private int id;
    private String nomor;
    private Date tanggal;
    private int suratJalanId;
    private int customerId;
    private String keterangan;
    private double subTotal;
    private double ppn;
    private double ppnNominal;
    private double diskon;
    private double total;
    private List<Integer> produkId;
    private List<Double> harga;
    private List<Double> qty;
    private List<Double> totalHarga;
}

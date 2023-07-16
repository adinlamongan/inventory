package com.adn.inventory.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PindahBarangRequestDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3583155541343721326L;
    
    private int id;
    private String nomor;
    private Date tanggal;
    private int gudangAsalId;
    private int gudangTujuanId;
    private String keterangan;
    private List<Integer> stokId;
    private List<Integer> produkId;
    private List<Double> qty;
}

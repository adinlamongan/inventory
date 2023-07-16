package com.adn.inventory.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LpbRequestDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3583155541343721326L;
    
    private int id;
    private String nomor;
    private Date tanggal;
    private int purchaseOrderId;
    private String keterangan;
    private List<Integer> detailId;
    private List<Integer> purchaseOrderDetailId;
    private List<Integer> produkId;
    private List<Integer> gudangId;
    private List<Double> qty;
}

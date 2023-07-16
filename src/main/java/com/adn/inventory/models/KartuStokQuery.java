package com.adn.inventory.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KartuStokQuery {


    private int id;

    private String nomor;

    private Date tanggal;

    private String keterangan;
    private String kode;

    private String namaProduk;

    private double qty;
    private String namaSatuan;

    private int gudangId;
    private String namaGudang;

    private int stokId;

//    @Column("table_name")
//    private String tableName;
//    @Column("reference_id")
//    private int referenceId;
//    @Column("table_name_detail")
//    private String tableNameDetail;
//    @Column("reference_detail_id")
//    private int referenceDetailId;

    private String ref;

}

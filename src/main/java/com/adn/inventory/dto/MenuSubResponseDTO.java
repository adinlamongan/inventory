package com.adn.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuSubResponseDTO {
    private int id;
    private int menu_id;
    private String namaMenu;

    private String icon;

    private String nama;
    private int no_urut;
    private String url;

    private Boolean utama;
    private Boolean tambah;
    private Boolean detail;
    private Boolean edit;
    private Boolean hapus;
    private Boolean verifikasi;
    private Boolean batalVerifikasi;
    private Boolean print;
}

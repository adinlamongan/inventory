package com.adn.inventory.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "role_akses_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleAksesDetail {

    @Id
    private int id;

    @Column("role_id")
    private int roleId;

    @Column("menu_id")
    private int menuId;

    @Column("no_urut")
    private int noUrut;

    private String nama;
    private String url;
    private Boolean utama;
    private Boolean detail;
    private Boolean tambah;
    private Boolean edit;
    private Boolean hapus;
    private Boolean verifikasi;
    private Boolean batalVerifikasi;
    private Boolean print;

}

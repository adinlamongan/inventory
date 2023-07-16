package com.adn.inventory.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RoleSettingRequestDTO {

    private int roleId;

    private List<Integer> id;
    private List<Integer> menuId;
    private List<Integer> noUrut;
    private List<String> namaMenu;

    private List<String> nama;
    private List<String> url;

    private List<Boolean> utama;
    private List<Boolean> tambah;
    private List<Boolean> detail;
    private List<Boolean> edit;
    private List<Boolean> hapus;
    private List<Boolean> verifikasi;
    private List<Boolean> batalVerifikasi;
    private List<Boolean> print;
}

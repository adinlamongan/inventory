package com.adn.inventory.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GroupMenuResponseDTO {
    private String menu;
    private String icon;

    private Boolean aktif = false;


    private List<MenuSubResponseDTO> lisMenuSub;
}

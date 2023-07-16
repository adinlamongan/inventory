package com.adn.inventory.util;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class Akses {

    public boolean cek(Model model){
        return (Boolean) model.getAttribute("akses_menu");
    }

}

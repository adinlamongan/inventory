package com.adn.inventory.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseSuccessDTO {

    public Object getMesage() {
        Map<String, Object> object = new HashMap<>();
        object.put("message", "Succes");
        return object;
    }
}

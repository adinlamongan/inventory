package com.adn.inventory.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "role")
@Data
@NoArgsConstructor
public class Role {

    @Id
    private int id;

    private String name;
}

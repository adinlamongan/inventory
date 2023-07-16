package com.adn.inventory.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "todolist")
public class Todo {

    @Id
    private Integer id;

    private String todo;

    private int price;

}

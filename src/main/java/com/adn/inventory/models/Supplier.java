package com.adn.inventory.models;

import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.userdetails.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Table(name = "master_supplier")
public class Supplier extends AuditEntity {

    @Id
    private Integer id;

    private String kode;

    private String nama;

    private String alamat;

    private String telepon;

    private String fax;

}

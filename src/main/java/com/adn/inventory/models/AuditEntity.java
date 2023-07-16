package com.adn.inventory.models;

import com.adn.inventory.security.CustomUser;
import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public abstract class AuditEntity  {


    @Version
    private int version;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private int createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private int updatedBy;


}

package com.adn.inventory.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Table(name = "master_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser extends AuditEntity {

    @Id
    private Integer id;

    @Column("name")
    private String username;

    private String password;

    @Column("role_id")
    private int roleId;

    private Boolean enabled;


}

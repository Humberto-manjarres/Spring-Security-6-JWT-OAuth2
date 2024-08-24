package com.market.spring_security_market.persistence.entity.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore//para detener un bucle infinito
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<GrantedPermission> permissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GrantedPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<GrantedPermission> permission) {
        this.permissions = permission;
    }
}

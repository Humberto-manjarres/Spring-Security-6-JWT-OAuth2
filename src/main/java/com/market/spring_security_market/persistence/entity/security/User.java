package com.market.spring_security_market.persistence.entity.security;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "\"user\"")
public class User implements UserDetails { // la interfaz UserDetails representa al usuario logueado

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)//    válida que el usuario sea unicos
    private String username;

    private String name;
    private String password;

    /*@Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;*/

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null)return null;
        if (role.getPermissions() == null)return null;
        List<SimpleGrantedAuthority> authorities = role.getPermissions().stream()
                .map(each -> each.getOperation().getName())
                .map(each -> new SimpleGrantedAuthority(each))
                /*.map(each ->{
                    String permission = each.name();
                    return new SimpleGrantedAuthority(permission);
                })*/
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_".concat(this.role.getName())));//agregamos el Role del usuario a la lista de permisos.
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //Getter y Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

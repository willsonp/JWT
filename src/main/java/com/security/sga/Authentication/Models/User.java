package com.security.sga.Authentication.Models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.security.sga.Authentication.Enums.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;

    @NotBlank
    @Column(name = "first_name" , nullable = false, length = 50)
    private String first_name;

    @NotBlank    
    @Column(name = "last_name" , nullable = false, length = 50)
    private String last_name;

    @NotBlank
    @Column(name = "email" , nullable = false, length = 100 , unique = true)
    private String email;

    @NotBlank
    @Column(name = "password" , nullable = false)
    private String password;

    @Enumerated(EnumType.ORDINAL)
    private Roles role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role).stream().map(r -> (GrantedAuthority) () -> r.name()).toList();
    }

    @Override
    public String getUsername() {
        return email;
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

    @Override
    public String getPassword() {
        return password;
    }

}

package com.lofominhili.trendchat.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "_user")
@Data
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Size(max = 20)
    @NotNull
    @NotBlank
    @Column(name = "name", nullable = false, unique = true, length = 20)
    private String name;

    @Size(max = 25)
    @Column(name = "surname", nullable = false, unique = true, length = 25)
    private String surname;

    @Size(max = 255)
    @NotNull
    @NotBlank
    @Column(name = "username", nullable = false)
    private String username;

    @Size(max = 255)
    @NotNull
    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 40)
    @Column(name = "email", unique = true, length = 40)
    private String email;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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
    public String getUsername() { return username; }

    @Override
    public String getPassword() { return password; }
}

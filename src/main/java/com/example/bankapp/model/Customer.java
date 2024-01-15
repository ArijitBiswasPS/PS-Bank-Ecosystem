package com.example.bankapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Customer implements UserDetails {
    @Id
    @Column(length = 12)
    private String customerId;
    @Column(length = 20)
    private String customerName;
    @Column(length = 50)
    private String customerAddress;
    @Column(length = 10)
    private String customerGender;
    @Column(length = 12)
    private String customerAadhar;
    @Column(length = 10)
    private String customerPhone;
    @Column(length = 50)
    private String customerEmail;
    @Column(length = 50)
    private String customerPassword;
    private float customerBalance;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return customerPassword;
    }

    @Override
    public String getUsername() {
        return customerId;
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
}

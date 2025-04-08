package dev.petkevicius.groceryPriceChecker.domain.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @Id
    private String id;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    @Transient
    private String name;

    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    // TODO: when multiple OAuth2 providers are supported, this should be converted into a list
    private String provider;

    private String providerId;

    private boolean enabled;
}


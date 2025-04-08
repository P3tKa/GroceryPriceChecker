package dev.petkevicius.groceryPriceChecker.domain.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authorities")
public class Authority implements GrantedAuthority {

    @Id
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
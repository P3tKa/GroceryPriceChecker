package dev.petkevicius.groceryPriceChecker.mapper.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public abstract class HashSetMixin {

    @JsonCreator
    public HashSetMixin(@JsonProperty("elements") Set<GrantedAuthority> elements) {}
}

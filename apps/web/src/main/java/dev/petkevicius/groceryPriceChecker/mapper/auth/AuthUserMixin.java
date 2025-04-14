package dev.petkevicius.groceryPriceChecker.mapper.auth;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.petkevicius.groceryPriceChecker.domain.auth.Authority;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AuthUserMixin {

    @JsonCreator
    public AuthUserMixin(
        @JsonProperty("id") String id,
        @JsonProperty("email") String email,
        @JsonProperty("encodedPassword") String encodedPassword,
        @JsonProperty("authorities") Set<Authority> authorities,
        @JsonProperty("enabled") boolean enabled
    ) {
    }
}
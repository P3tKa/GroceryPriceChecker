package dev.petkevicius.groceryPriceChecker.mapper.auth;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.petkevicius.groceryPriceChecker.domain.auth.Authority;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class AuthUserMixin {

    @JsonCreator
    public AuthUserMixin(
        @JsonProperty("id") String id,
        @JsonProperty("username") String username,
        @JsonProperty("email") String email,
        @JsonProperty("provider") String provider,
        @JsonProperty("providerId") String providerId,
        @JsonProperty("authorities") Set<Authority> authorities
    ) {
    }
}
package dev.petkevicius.groceryPriceChecker.mapper.auth;

import java.util.HashSet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.petkevicius.groceryPriceChecker.domain.auth.AuthUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.addMixIn(AuthUser.class, AuthUserMixin.class);
        mapper.addMixIn(HashSet.class, HashSetMixin.class);

        return mapper;
    }
}

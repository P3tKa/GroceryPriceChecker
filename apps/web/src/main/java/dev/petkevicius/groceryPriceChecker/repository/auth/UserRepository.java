package dev.petkevicius.groceryPriceChecker.repository.auth;

import java.util.Optional;

import dev.petkevicius.groceryPriceChecker.domain.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByProviderAndProviderId(String provider, String providerId);
}

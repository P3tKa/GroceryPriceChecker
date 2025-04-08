package dev.petkevicius.groceryPriceChecker.service.auth;

import dev.petkevicius.groceryPriceChecker.domain.auth.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Slf4j
public class AuthUtils {

    public static String getUserId(Authentication authentication) throws IllegalArgumentException {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "";
        }

        Object principal = authentication.getPrincipal();
        return switch (principal) {
            case AuthUser user -> getIdOrLogError(user.getId(), "AuthUser");
            case OAuth2User oauth2User -> getIdOrLogError(oauth2User.getAttribute("id"), "OAuth2User");
            default -> {
                log.error("Unsupported authentication principal type: {}", principal.getClass().getName());
                yield "";
            }
        };
    }

    private static String getIdOrLogError(String id, String principalType) {
        if (id == null || id.isEmpty()) {
            log.error("id is missing in {} principal", principalType);
            return "";
        }
        return id;
    }
}

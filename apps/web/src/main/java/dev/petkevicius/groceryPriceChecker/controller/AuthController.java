package dev.petkevicius.groceryPriceChecker.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import dev.petkevicius.groceryPriceChecker.domain.auth.Authority;
import dev.petkevicius.groceryPriceChecker.domain.auth.User;
import dev.petkevicius.groceryPriceChecker.service.auth.UserDetailsServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final PasswordEncoder encoder;
    private final UserDetailsServiceImpl userDetailsService;

    @PersistenceContext
    private EntityManager entityManager;

    public AuthController(
        PasswordEncoder encoder,
        UserDetailsServiceImpl userDetailsService
    ) {
        this.encoder = encoder;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/auth/signUp")
    public String getSignUpComponent(Model model) {
        model.addAttribute("errors", List.of());
        return "components/auth/signUp";
    }

    @GetMapping("/auth/signIn")
    public String getSignInComponent() {
        return "components/auth/signIn";
    }

    @PostMapping("/auth/signUp")
    public String signUp(
        @RequestParam String username,
        @RequestParam String email,
        @RequestParam String password,
        @RequestParam String confirmPassword,
        Model model
    ) {
        List<String> errors = new ArrayList<>();
        if(username.length() > 64) {
            errors.add("USERNAME_TOO_LONG");
        }
        if (username.length() < 3) {
            errors.add("USERNAME_TOO_SHORT");
        }
        if(email.length() > 64) {
            errors.add("EMAIL_TOO_LONG");
        }
        if (password.length() < 8) {
            errors.add("PASSWORD_TOO_SHORT");
        }
        if(password.length() > 64) {
            errors.add("PASSWORD_TOO_LONG");
        }
        if(!password.equals(confirmPassword)) {
            errors.add("PASSWORDS_DO_NOT_MATCH");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "components/auth/signUp";
        }

        try {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(encoder.encode(password));
            user.setAuthorities(Set.of(entityManager.getReference(Authority.class, "USER")));
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);

            userDetailsService.save(user);

            model.addAttribute("SIGN_UP_SUCCESS", true);
            return "components/auth/signIn";
        } catch (IllegalArgumentException e) {
            errors.add("EMAIL_ALREADY_EXISTS");
            model.addAttribute("errors", errors);
            return "components/auth/signUp";
        }
    }
}

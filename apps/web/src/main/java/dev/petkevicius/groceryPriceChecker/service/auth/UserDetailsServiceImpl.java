package dev.petkevicius.groceryPriceChecker.service.auth;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import dev.petkevicius.groceryPriceChecker.domain.auth.AuthUser;
import dev.petkevicius.groceryPriceChecker.domain.auth.User;
import dev.petkevicius.groceryPriceChecker.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email).orElseThrow(
            () -> new UsernameNotFoundException("User not found with email: " + email)
        );

        Set<GrantedAuthority> authorities = user.getAuthorities().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
            .collect(Collectors.toSet());

        return new AuthUser(
            user.getId(),
            user.getEmail(),
            user.getPassword(),
            authorities
        );
    }

    public void save(User user) throws IllegalArgumentException {
        Optional<User> databaseUser = userRepository.findUserByEmail(user.getEmail());

        databaseUser.ifPresentOrElse(
            existingUser -> {
                if (existingUser.getPassword() != null) {
                    throw new IllegalArgumentException("Email already in use");
                }
                existingUser.setPassword(user.getPassword());
                userRepository.save(existingUser);
            },
            () -> userRepository.save(user));
    }

    public User getUser(String email) throws IllegalArgumentException {
        return userRepository
            .findUserByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}

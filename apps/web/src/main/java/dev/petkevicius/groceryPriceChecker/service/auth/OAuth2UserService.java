package dev.petkevicius.groceryPriceChecker.service.auth;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import dev.petkevicius.groceryPriceChecker.domain.auth.Authority;
import dev.petkevicius.groceryPriceChecker.domain.auth.User;
import dev.petkevicius.groceryPriceChecker.repository.auth.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        return processOAuth2User(oAuth2UserRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        String name = attributes.getOrDefault("name", "").toString();
        String providerId = attributes.getOrDefault("sub", "").toString();
        String email = attributes.getOrDefault("email", "").toString();

        String oauthProvider = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        Optional<User> providerUser = userRepository.findUserByProviderAndProviderId(oauthProvider, providerId);
        User user = providerUser.orElseGet(() -> userRepository.findUserByEmail(email)
            .map(existingUser -> updateUser(existingUser, oauthProvider, providerId))
            .orElseGet(() -> createUser(name, email, oauthProvider, providerId)));

        Set<GrantedAuthority> authorities = new HashSet<>(oAuth2User.getAuthorities());

        user.getAuthorities().forEach(auth ->
            authorities.add(new SimpleGrantedAuthority(auth.getAuthority()))
        );

        attributes.put("id", user.getId());

        return new DefaultOAuth2User(authorities, attributes, "sub");
    }

    private User createUser(String name, String email, String provider, String providerId) {
        User user = User.builder()
            .id(UUID.randomUUID().toString())
            .username(name)
            .email(email)
            .provider(provider)
            .providerId(providerId)
            .authorities(Set.of(entityManager.getReference(Authority.class, "USER")))
            .build();
        log.info("Created new user: {}", user.getUsername());
        return userRepository.save(user);
    }

    private User updateUser(User user, String provider, String providerId) {
        user.setProvider(provider);
        user.setProviderId(providerId);
        log.info("Updated user: {}", user.getUsername());
        return userRepository.save(user);
    }
}

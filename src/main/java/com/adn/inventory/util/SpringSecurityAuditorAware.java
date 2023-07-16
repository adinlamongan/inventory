package com.adn.inventory.util;

import com.adn.inventory.models.AppUser;
import com.adn.inventory.security.CustomUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<Integer> {


    @Override
    public Optional<Integer> getCurrentAuditor() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        CustomUser user = (CustomUser) securityContext.getAuthentication().getPrincipal();
        return Optional.of(user.getUserId());
    }
}

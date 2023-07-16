package com.adn.inventory.util;

import com.adn.inventory.models.AppUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableJdbcAuditing
public class Config {
    @Bean
    AuditorAware<Integer> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }
}

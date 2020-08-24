package me.jerry.example.webflux.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static me.jerry.example.webflux.constant.SecurityConstants.*;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    @ConfigurationProperties(prefix = "security.user.admin")
    public SecurityUserCredential adminUser() {
        return new SecurityUserCredential();
    }

    @Bean
    @ConfigurationProperties(prefix = "security.user.actuator")
    public SecurityUserCredential actuatorUser() {
        return new SecurityUserCredential();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername(adminUser().getUsername())
                .password(PASSWORD_PREFIX_NOOP + adminUser().getPassword())
                .roles(ROLE_ADMIN, ROLE_ACTUATOR, ROLE_SYSTEM)
                .build();
        UserDetails actuator = User.withUsername(actuatorUser().getUsername())
                .password(PASSWORD_PREFIX_NOOP + actuatorUser().getPassword())
                .roles(ROLE_ACTUATOR)
                .build();
        return new MapReactiveUserDetailsService(admin, actuator);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // @formatter:off
        http
                .httpBasic().and()

                .authorizeExchange()

                // actuator - health, info, hystrix
                .matchers(EndpointRequest.to(HealthEndpoint.class, InfoEndpoint.class)).permitAll()

                // actuator - others
                .matchers(EndpointRequest.toAnyEndpoint()).hasRole(ROLE_ACTUATOR)

                // others - all open
                .anyExchange().permitAll();

        // csrf disabled
        http.csrf().disable();
        // @formatter:on

        return http.build();
    }

    @Data
    @ToString(exclude = "password")
    class SecurityUserCredential {
        private String username;
        private String password;
    }

}

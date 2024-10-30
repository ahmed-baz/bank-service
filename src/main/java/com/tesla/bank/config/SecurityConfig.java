package com.tesla.bank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/api/v1/balance/*", "/api/v1/loans/*", "/api/v1/cards/*", "/api/v1/accounts/*").authenticated()
                .requestMatchers("/api/v1/contacts/**", "/error").permitAll()
        );

        //http.formLogin(AbstractHttpConfigurer::disable);
        //http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User
                .withUsername("admin@tesla.com")
                .password("{bcrypt}$2a$12$F9s8wf9Jd0iKP4zW1mlxoOg7ptPc6E4qOEVpWVmxnhGcvH8.8ra2a")
                .authorities("admin").build();
        UserDetails maker = User
                .withUsername("maker@tesla.com")
                .password("{bcrypt}$2a$12$3CHj8.DAqOTqwbKM6Az/2e0Ej08sVXBaeWPVKsL45P3RCvxyXiI52")
                .authorities("maker").build();
        UserDetails checker = User
                .withUsername("checker@tesla.com")
                .password("{bcrypt}$2a$12$3CHj8.DAqOTqwbKM6Az/2e0Ej08sVXBaeWPVKsL45P3RCvxyXiI52")
                .authorities("checker").build();

        UserDetails user = User.withUsername("ahmedbaz").password("{noop}12345").authorities("read").build();
        return new InMemoryUserDetailsManager(admin, maker, checker, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}

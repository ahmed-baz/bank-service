package com.tesla.bank.config;

import com.tesla.bank.handler.CustomAccessDeniedHandler;
import com.tesla.bank.handler.CustomBasicAuthEntryPointHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
//@Profile("prod")
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/v1/balance/*", "/api/v1/loans/*", "/api/v1/cards/*", "/api/v1/accounts/*").authenticated()
                        .requestMatchers("/api/v1/customers/*", "/api/v1/contacts/**", "/error").permitAll()
                );

        //http.formLogin(AbstractHttpConfigurer::disable);
        //http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(withDefaults());
        http.httpBasic(basicConfig -> basicConfig.authenticationEntryPoint(new CustomBasicAuthEntryPointHandler()));
        http.exceptionHandling(exConfig -> exConfig.accessDeniedHandler(new CustomAccessDeniedHandler()));
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
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

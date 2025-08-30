package com.example.SpringSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        var admin = User.withUsername("admin").password(encoder.encode("admin123")).roles("ADMIN").build();
        var librarian = User.withUsername("lib123").password(encoder.encode("lib123")).roles("LIBRARIAN").build();
        var student = User.withUsername("student").password(encoder.encode("student123")).roles("STUDENT").build();
        var guest = User.withUsername("guest").password(encoder.encode("guest123")).roles("GUEST").build();

        return new InMemoryUserDetailsManager(admin, librarian, student, guest);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/about", "/login", "/books/public").permitAll()
                        .requestMatchers("/books", "/books/{id}", "/books/{id}/reserve").hasRole("STUDENT")
                        .requestMatchers("/books/**", "/reservations/**").hasRole("LIBRARIAN")
                        .requestMatchers("/admin/**", "/users").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/login?logout=true").permitAll())
                .exceptionHandling(ex -> ex.accessDeniedPage("/access-denied"))
                .csrf(AbstractHttpConfigurer::disable)
        ;
        return http.build();
    }
}

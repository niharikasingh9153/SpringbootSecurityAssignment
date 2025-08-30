package com.example.demo.Configuration;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Enable method-level security
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF for simplicity
                .authorizeRequests()
                .antMatchers("/", "/about", "/login", "/books/public").permitAll() // Public pages
                .antMatchers("/books", "/books/*/reserve").hasRole("STUDENT") // Student access
                .antMatchers("/books", "/books/*").hasRole("LIBRARIAN") // Librarian access
                .antMatchers("/books/*/delete", "/admin/reports", "/users").hasRole("ADMIN") // Admin access
                .anyRequest().authenticated() // Any other request needs authentication
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll() // Allow anyone to access the login page
                .and()
                .logout()
                .permitAll(); // Allow anyone to log out
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // For password encryption
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // In-memory authentication with encoded passwords
        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN")
                .and()
                .withUser("librarian").password(passwordEncoder().encode("lib123")).roles("LIBRARIAN")
                .and()
                .withUser("student").password(passwordEncoder().encode("student123")).roles("STUDENT")
                .and()
                .withUser("guest").password(passwordEncoder().encode("guest123")).roles("GUEST");
    }
}

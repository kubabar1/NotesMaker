package com.od.notesmaker.config;

import com.od.notesmaker.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import static java.util.Arrays.asList;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(appUserDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint) //deafult entry point returns FULL PAGE unauthorized, not well suited for rest login
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .successHandler((HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
                    response.setStatus(200);
                })
                .failureHandler((HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) -> {
                    response.setStatus(401);
                })
                .permitAll()
                .and()
                .csrf()
                .ignoringAntMatchers("/logout", "/login")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .logout()
                .permitAll()
                .logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)))
                .deleteCookies("JSESSIONID", "XSRF-TOKEN")
                .invalidateHttpSession(true);
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(asList("https://localhost:3000"));
        configuration.setAllowedMethods(asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(asList("Authorization", "Cache-Control", "Content-Type", "credentials", "X-XSRF-TOKEN"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    /*@Bean
    public PasswordEncoder encoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                String sha256hex1 = Hashing.sha256().hashString(rawPassword, StandardCharsets.UTF_8).toString();
                String sha256hex2 = Hashing.sha256().hashString(sha256hex1, StandardCharsets.UTF_8).toString();
                String sha256hex3 = Hashing.sha256().hashString(sha256hex2, StandardCharsets.UTF_8).toString();
                return sha256hex3;
            }
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String sha256hex1 = Hashing.sha256().hashString(rawPassword, StandardCharsets.UTF_8).toString();
                String sha256hex2 = Hashing.sha256().hashString(sha256hex1, StandardCharsets.UTF_8).toString();
                String sha256hex3 = Hashing.sha256().hashString(sha256hex2, StandardCharsets.UTF_8).toString();
                return encodedPassword.equals(sha256hex3);
            }

        };
    }*/
}

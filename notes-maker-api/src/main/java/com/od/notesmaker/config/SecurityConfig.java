package com.od.notesmaker.config;

import com.google.common.hash.Hashing;
import com.od.notesmaker.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

    //@Value("${spring.security.scanning.token}")
    //private String scanningToken;


    /*@Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select login,password,1 as enabled from users where login=?")
                .authoritiesByUsernameQuery(
                        "select login, 'USER' from users where login=?");
    }*/

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

    /*@Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password(encoder().encode("adminPass")).roles("ADMIN")
                .and()
                .withUser("user").password(encoder().encode("userPass")).roles("USER");
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .cors().and()
                //.addFilterBefore(new TokenAuthenticationFilter(scanningToken), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //.and()
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
                .logout();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(asList("*"));
        configuration.setAllowedMethods(asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(asList("Authorization", "Cache-Control", "Content-Type", "credentials"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /*@Bean(name="scanningToken")
    public String scanningToken() {
        return scanningToken;
    }*/

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(){
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                try {
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(200)+100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return super.matches(rawPassword, encodedPassword);
            }
        };
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

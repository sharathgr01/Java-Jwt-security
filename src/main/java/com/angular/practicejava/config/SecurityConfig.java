package com.angular.practicejava.config;

import com.angular.practicejava.security.JWTAuthenticationEntryPoint;
import com.angular.practicejava.security.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    private UserService userService;

    @Autowired
    @Lazy
    private JWTAuthenticationFilter jwtFilter;

    @Autowired
    private JWTAuthenticationEntryPoint jwtPoint;

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withUsername("Sharath")
                .password(passwordEncoder().encode("secret"))
                .roles("admin").build();
        UserDetails user1 = User.builder().username("Sharath").password("secret").roles("ADMIN").build();
        return new InMemoryUserDetailsManager(userDetails);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(Customizer.withDefaults())
//                .authorizeHttpRequests()
//                .requestMatchers("products/welcome").permitAll()
//                .and().authorizeHttpRequests().requestMatchers("/products/**").authenticated().and().formLogin();
//        return http.csrf(Customizer.withDefaults()).addFilter(jwtFilter)
//                .authorizeHttpRequests(req -> req
//                        .requestMatchers("/getRecipes").permitAll()
//                        .anyRequest().authenticated())
//                .userDetailsService(userService).build();
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.
                requestMatchers("/getRecipes").permitAll().requestMatchers("/login").permitAll()
                .anyRequest()
                .authenticated());
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }


}

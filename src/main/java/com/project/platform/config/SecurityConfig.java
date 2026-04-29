package com.project.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // 所有人都能访问
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 仅管理员
                        .requestMatchers("/merchant/**").hasRole("MERCHANT") // 仅商家
                        .anyRequest().authenticated() // 其他都要登录
                )

                .formLogin(form -> form.permitAll())

                .logout(logout -> logout.permitAll());

        return http.build();
    }

}

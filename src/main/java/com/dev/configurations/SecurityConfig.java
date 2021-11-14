package com.dev.configurations;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // CRIPTOGRAFIA DE SENHAS
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // CONFIGURAÇÕES DE AUTENTICAÇÃO (1)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // EM MEMÓRIA
        auth
                .inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("JOSE")
                .password(passwordEncoder().encode("123"))
                .roles("OPERADOR");
    }

    // CONFIGURAÇÕES DE AUTORIZAÇÃO (2)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }
}

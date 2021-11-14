package com.dev.configurations;


import com.dev.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // INJEÇÃO DO PROVEDOR DE USUÁRIOS
    @Autowired
    private UsuarioService usuarioService;

    // CRIPTOGRAFIA DE SENHAS
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // CONFIGURAÇÕES DE AUTENTICAÇÃO (1)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // EM MEMÓRIA
        /*
        auth
                .inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("JOSE")
                .password(passwordEncoder().encode("123"))
                .roles("OPERADOR")
                .and()
                .passwordEncoder(passwordEncoder())
                .withUser("MARIA")
                .password(passwordEncoder().encode("000"))
                .roles("ADMIN")
        */
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder())
        ;
    }

    // CONFIGURAÇÕES DE AUTORIZAÇÃO (2)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http
                .csrf().disable()  // DESABILITANDO A PROTEÇÃO CSRF pois apenas é necessário quando você tem envios de formulários da web que são propensos a "solicitações entre sites" nas outras guias do mesmo navegador
                .authorizeRequests() // INÍCIO DA CONFIG
                .antMatchers("/api/operador/acesso-operador").hasAnyRole("OPERADOR", "ADMIN")
                .antMatchers("/api/admin/acesso-admin").hasRole("ADMIN")
                .antMatchers("/api/public").permitAll()
                .antMatchers("/api/cadastrar-usuario").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
        // .and().formLogin(); // BASEADA EM UM FORMULÁRIO POST /login {name:username, name:password}
                .and().httpBasic()
                ;



    }
}

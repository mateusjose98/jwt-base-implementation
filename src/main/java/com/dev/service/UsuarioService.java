package com.dev.service;

import com.dev.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {



        if (!username.equals("MATEUS")) {
            System.out.println(username);

            throw new UsernameNotFoundException("Usuário não encontrado!");
        }


        return User.builder()
                .username("MATEUS")
                .password(passwordEncoder.encode("123"))
                .roles("OPERADOR", "ADMIN")
                .build();
    }
}

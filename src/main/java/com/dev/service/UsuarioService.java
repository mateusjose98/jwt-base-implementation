package com.dev.service;

import com.dev.model.Usuario;
import com.dev.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserDetails autenticar(Usuario usuario) {
        UserDetails u = loadUserByUsername(usuario.getLogin());
         boolean senhasOk = passwordEncoder.matches(usuario.getSenha(), u.getPassword());
        if(senhasOk){
            return u;
        }

        throw new RuntimeException("Senha inválida");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        List<String> roles = usuario.getRoles().stream().map(i -> i.getNome()).collect(Collectors.toList());

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles.toArray(new String[0]))
                .build();




    // MOCK
        /*
        if (!username.equals("MATEUS")) {
            System.out.println(username);

            throw new UsernameNotFoundException("Usuário não encontrado!");
        }


        return User.builder()
                .username("MATEUS")
                .password(passwordEncoder.encode("123"))
                .roles("OPERADOR", "ADMIN")
                .build();
        */
    }

    @Transactional
    public Usuario salvar(Usuario usr) {
        return usuarioRepository.save(usr);
    }
}

package com.dev;

import com.dev.model.Role;
import com.dev.model.Usuario;
import com.dev.repositories.RoleRepository;
import com.dev.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ProjetoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProjetoApplication.class, args);
    }

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Transactional
    @Override
    public void run(String... args) throws Exception {

        Role roleAdmin = new Role();
        roleAdmin.setNome("ADMIN");

        Role roleOperador = new Role();
        roleOperador.setNome("OPERADOR");
        List<Role> roles = Arrays.asList(roleAdmin, roleOperador);
        roleAdmin = roleRepository.save(roleAdmin);
        roleOperador = roleRepository.save(roleOperador);

        Usuario jose = new Usuario(); // JOSÉ É ADMIN E OPERADOR
        jose.setNome("José Mateus");
        jose.setLogin("JMTV");
        jose.setSenha(new BCryptPasswordEncoder().encode("123"));
        jose.setRoles(Arrays.asList(roleAdmin,roleOperador));

        Usuario maria = new Usuario(); //MARIA É OPERADORA
        maria.setNome("Maria da Silva Santos");
        maria.setLogin("MARIAZINHA");
        maria.setSenha(new BCryptPasswordEncoder().encode("123"));
        maria.setRoles(Arrays.asList(roleOperador));

        usuarioRepository.saveAllAndFlush(Arrays.asList(jose, maria));
    }
}

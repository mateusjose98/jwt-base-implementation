package com.dev;

import com.dev.model.Role;
import com.dev.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class ProjetoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProjetoApplication.class, args);
    }

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        Role roleAdmin = new Role();
        roleAdmin.setNome("ADMIN");

        Role roleOperador = new Role();
        roleOperador.setNome("OPERADOR");
        roleRepository.saveAll(Arrays.asList(roleAdmin, roleOperador));
    }
}

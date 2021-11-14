package com.dev.controllers;

import com.dev.model.Role;
import com.dev.model.Usuario;
import com.dev.repositories.RoleRepository;
import com.dev.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/api")
public class MainController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    // localhost:8080/api/admin/acesso-admin
    @GetMapping(value = "/admin/acesso-admin")
    public String admin() {
        return "Seu acesso foi autozado para a área de admin";
    }

    // localhost:8080/api/public
    @GetMapping(value = "/public")
    public String publico() {
        return "Seu acesso foi autozado para a área de pública <não requer login>";
    }

    // localhost:8080/api/operador/acesso-operador
    @GetMapping(value = "/operador/acesso-operador")
    public String operador() {
        return "Seu acesso foi autozado para a área de operador";
    }

    // localhost:8080/api/outra-requisicao
    @GetMapping(value = "/outra-requisicao")
    public String outraRota() {
        return "Apenas autenticação com QUALQUER usuário foi requerida!";
    }

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping(value = "/cadastrar-usuario")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody Usuario usuario) {
        // TODO validar a nulidade da senha
        Role r = roleRepository.findById(usuario.getRoles().get(0).getId()).get();
        usuario.setRoles(Arrays.asList(r));
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha())); //CRIPTOGRAFAR SENHA

       return usuarioService.salvar(usuario);

    }

}

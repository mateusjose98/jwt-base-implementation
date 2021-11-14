package com.dev.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class MainController {

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
}

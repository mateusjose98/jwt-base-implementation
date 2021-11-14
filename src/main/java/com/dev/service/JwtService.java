package com.dev.service;

import com.dev.ProjetoApplication;
import com.dev.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${spring.jwt.expiracao}")
    private String expiracao;

    @Value("${spring.jwt.chave}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario) {
        long exp = Long.valueOf(expiracao);
        LocalDateTime dataHoraExp = LocalDateTime
                        .now()
                        .plusMinutes(exp);
        Instant instant = dataHoraExp.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        Map<String, Object> claims = new HashMap<>();
        claims.put("Nome", usuario.getNome());


        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getLogin())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();


    }


    private Claims obterClaims (String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean tokenValido(String token) {
        try {
            var claims = obterClaims(token);
            var expiracao = claims.getExpiration();
            var expiracaoLocalDate = expiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(expiracaoLocalDate);
        } catch (Exception e) {
            return false;
        }

    }

    public String objetLoginUsuario (String token) {
        return (String) obterClaims(token).getSubject();
    }

    // APENAS PARA FINS DE TESTE
    public static void mainMetodo(String[] args) {
        var contexto = SpringApplication.run(ProjetoApplication.class);
        JwtService s = contexto.getBean(JwtService.class);
                Usuario usuario = new Usuario();
        usuario.setNome("Mateus");
        usuario.setLogin("120.120.20.10");

        String token = s.gerarToken(usuario);

        var claims = s.obterClaims(token);

        var login = s.objetLoginUsuario(token);


        System.out.println(String.format("TOKEN: %s, CLAIMS: %s, LOGIN: %s", token, claims, login) );

    }

}

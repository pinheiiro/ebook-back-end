package com.pinheiro.ebook.servicos;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.pinheiro.ebook.entidades.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    //@Value("${api.security.token.secret}")
    private String secret = "1kXsE4kBObaSEkKwY3RjldfM1AcnM2HLe96yfsUHI7r";

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("ebook-api")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))
                    .sign(algoritmo);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            String subject = JWT.require(algoritmo)
                    .withIssuer("ebook-api")
                    .build()
                    .verify(token)
                    .getSubject();
            return subject;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Token inválido ou expirado", exception);
        }
    }
}

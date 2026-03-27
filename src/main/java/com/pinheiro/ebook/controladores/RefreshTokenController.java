package com.pinheiro.ebook.controladores;

import com.pinheiro.ebook.dtos.RefreshTokenDTO;
import com.pinheiro.ebook.dtos.RefreshTokenRenovacaoDTO;
import com.pinheiro.ebook.dtos.TokenRenovadoDTO;
import com.pinheiro.ebook.servicos.RefreshTokenService;
import com.pinheiro.ebook.servicos.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/refresh")
public class RefreshTokenController {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> renovarToken(@Valid @RequestBody RefreshTokenRenovacaoDTO dto) {
        try {
            // Validar se o refresh token ainda é válido
            RefreshTokenDTO refreshToken = refreshTokenService.validarRefreshToken(dto.refreshToken());

            // Gerar novo token JWT
            String novoToken = tokenService.gerarToken(refreshToken.usuario());

            return ResponseEntity.ok(new TokenRenovadoDTO(novoToken, dto.refreshToken()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorDTO("Refresh token inválido ou expirado"));
        }
    }

    @DeleteMapping("/{token}")
    public ResponseEntity<Void> revogarToken(@PathVariable String token) {
        try {
            refreshTokenService.revogarToken(token);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DTO para resposta de erro
    record ErrorDTO(String mensagem) {
    }
}


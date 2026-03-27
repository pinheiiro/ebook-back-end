package com.pinheiro.ebook.servicos;

import com.pinheiro.ebook.dtos.RefreshTokenDTO;
import com.pinheiro.ebook.dtos.UsuarioDTO;
import com.pinheiro.ebook.entidades.RefreshToken;
import com.pinheiro.ebook.entidades.Usuario;
import com.pinheiro.ebook.repositorios.RefreshTokenRepository;
import com.pinheiro.ebook.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Value("${api.security.refresh-token.expiration}")
    private long refreshTokenExpiration;

    public RefreshTokenDTO criarRefreshToken(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        refreshTokenRepository.deleteByUsuarioId(usuarioId);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUsuario(usuario);
        refreshToken.setDataExpiracao(Instant.now().plusMillis(refreshTokenExpiration));

        RefreshToken saved = refreshTokenRepository.save(refreshToken);
        return toDTO(saved);
    }

    public RefreshTokenDTO validarRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token não encontrado"));

        if (refreshToken.getDataExpiracao().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expirado");
        }

        return toDTO(refreshToken);
    }

    public boolean verificarSeExpirou(String token) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token);
        if (refreshToken.isEmpty()) {
            return true;
        }
        return refreshToken.get().getDataExpiracao().isBefore(Instant.now());
    }

    public RefreshTokenDTO renovarToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token não encontrado"));

        if (refreshToken.getDataExpiracao().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expirado");
        }

        refreshToken.setDataExpiracao(Instant.now().plusMillis(refreshTokenExpiration));
        RefreshToken updated = refreshTokenRepository.save(refreshToken);

        return toDTO(updated);
    }

    public void revogarToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token não encontrado"));

        refreshTokenRepository.delete(refreshToken);
    }

    public void revogarTokenPorUsuarioId(Long usuarioId) {
        refreshTokenRepository.deleteByUsuarioId(usuarioId);
    }

    public RefreshTokenDTO obterPorId(Long id) {
        RefreshToken refreshToken = refreshTokenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Refresh token não encontrado"));
        return toDTO(refreshToken);
    }

    private RefreshTokenDTO toDTO(RefreshToken refreshToken) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                refreshToken.getUsuario().getId(),
                refreshToken.getUsuario().getNome(),
                refreshToken.getUsuario().getEmail(),
                refreshToken.getUsuario().getDataCriacao(),
                refreshToken.getUsuario().getUltimaAtualizacao()
        );

        return new RefreshTokenDTO(
                refreshToken.getId(),
                refreshToken.getToken(),
                refreshToken.getDataExpiracao(),
                usuarioDTO
        );
    }
}


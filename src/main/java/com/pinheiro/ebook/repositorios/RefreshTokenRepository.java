package com.pinheiro.ebook.repositorios;

import com.pinheiro.ebook.entidades.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUsuarioId(Long usuarioId);
    void deleteByUsuarioId(Long usuarioId);
}


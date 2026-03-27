package com.pinheiro.ebook.repositorios;

import com.pinheiro.ebook.entidades.Perfis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfisRepository extends JpaRepository<Perfis, Long> {
    Optional<Perfis> findByNome(String nome);
}


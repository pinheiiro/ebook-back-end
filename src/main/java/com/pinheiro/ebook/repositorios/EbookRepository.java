package com.pinheiro.ebook.repositorios;

import com.pinheiro.ebook.entidades.Ebook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EbookRepository extends JpaRepository<Ebook, Long> {
}

package com.pinheiro.ebook.servicos;

import com.pinheiro.ebook.dtos.PerfisCreateDTO;
import com.pinheiro.ebook.dtos.PerfisDTO;
import com.pinheiro.ebook.dtos.PerfisUpdateDTO;
import com.pinheiro.ebook.entidades.Perfis;
import com.pinheiro.ebook.repositorios.PerfisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerfisService {

    @Autowired
    private PerfisRepository perfisRepository;

    public PerfisDTO create(PerfisCreateDTO dto) {
        // Verificar se o perfil já existe
        if (perfisRepository.findByNome(dto.nome()).isPresent()) {
            throw new RuntimeException("Perfil com nome '" + dto.nome() + "' já existe");
        }

        Perfis perfis = new Perfis();
        perfis.setNome(dto.nome());
        Perfis saved = perfisRepository.save(perfis);
        return toDTO(saved);
    }

    public PerfisDTO update(Long id, PerfisUpdateDTO dto) {
        Perfis perfis = perfisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        // Verificar se outro perfil já possui este nome
        if (perfisRepository.findByNome(dto.nome()).isPresent()) {
            Perfis existente = perfisRepository.findByNome(dto.nome()).get();
            if (!existente.getId().equals(id)) {
                throw new RuntimeException("Perfil com nome '" + dto.nome() + "' já existe");
            }
        }

        perfis.setNome(dto.nome());
        Perfis saved = perfisRepository.save(perfis);
        return toDTO(saved);
    }

    public PerfisDTO findById(Long id) {
        Perfis perfis = perfisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        return toDTO(perfis);
    }

    public PerfisDTO findByNome(String nome) {
        Perfis perfis = perfisRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
        return toDTO(perfis);
    }

    public List<PerfisDTO> findAll() {
        return perfisRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (!perfisRepository.existsById(id)) {
            throw new RuntimeException("Perfil não encontrado");
        }
        perfisRepository.deleteById(id);
    }

    public Perfis obterPerfisEntidade(Long id) {
        return perfisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
    }

    public Perfis obterPerfisEntidadePorNome(String nome) {
        return perfisRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
    }

    private PerfisDTO toDTO(Perfis perfis) {
        return new PerfisDTO(
                perfis.getId(),
                perfis.getNome()
        );
    }
}


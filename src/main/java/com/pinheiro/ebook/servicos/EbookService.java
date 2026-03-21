package com.pinheiro.ebook.servicos;

import com.pinheiro.ebook.dtos.EbookCreateDTO;
import com.pinheiro.ebook.dtos.EbookDTO;
import com.pinheiro.ebook.dtos.EbookUpdateDTO;
import com.pinheiro.ebook.dtos.UsuarioDTO;
import com.pinheiro.ebook.entidades.Ebook;
import com.pinheiro.ebook.entidades.Usuario;
import com.pinheiro.ebook.repositorios.EbookRepository;
import com.pinheiro.ebook.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EbookService {

    @Autowired
    private EbookRepository ebookRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public EbookDTO create(EbookCreateDTO dto) {
        /*
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario not found"));
         */
        Ebook ebook = new Ebook();
        ebook.setTitulo(dto.titulo());
        ebook.setAutor(dto.autor());
        ebook.setCategoria(dto.categoria());
        ebook.setUrlCapa(dto.urlCapa());
        ebook.setTexto(dto.texto());
        //ebook.setUsuario(usuario);
        Ebook saved = ebookRepository.save(ebook);
        return toDTO(saved);
    }

    public EbookDTO update(Long id, EbookUpdateDTO dto) {
        Ebook ebook = ebookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ebook not found"));
        if (dto.titulo() != null) ebook.setTitulo(dto.titulo());
        if (dto.autor() != null) ebook.setAutor(dto.autor());
        if (dto.categoria() != null) ebook.setCategoria(dto.categoria());
        if (dto.urlCapa() != null) ebook.setUrlCapa(dto.urlCapa());
        if (dto.texto() != null) ebook.setTexto(dto.texto());
        Ebook saved = ebookRepository.save(ebook);
        return toDTO(saved);
    }

    public EbookDTO findById(Long id) {
        Ebook ebook = ebookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ebook not found"));
        return toDTO(ebook);
    }

    public List<EbookDTO> findAll() {
        return ebookRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (!ebookRepository.existsById(id)) {
            throw new RuntimeException("Ebook not found");
        }
        ebookRepository.deleteById(id);
    }

    private EbookDTO toDTO(Ebook ebook) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                ebook.getUsuario().getId(),
                ebook.getUsuario().getNome(),
                ebook.getUsuario().getEmail(),
                ebook.getUsuario().getDataCriacao(),
                ebook.getUsuario().getUltimaAtualizacao()
        );
        return new EbookDTO(
                ebook.getId(),
                ebook.getTitulo(),
                ebook.getAutor(),
                ebook.getCategoria(),
                ebook.getUrlCapa(),
                ebook.getTexto(),
                ebook.getDataCriacao(),
                ebook.getUltimaAtualizacao(),
                usuarioDTO
        );
    }
}

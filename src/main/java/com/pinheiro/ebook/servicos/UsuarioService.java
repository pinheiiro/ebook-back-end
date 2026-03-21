package com.pinheiro.ebook.servicos;

import com.pinheiro.ebook.dtos.UsuarioCreateDTO;
import com.pinheiro.ebook.dtos.UsuarioDTO;
import com.pinheiro.ebook.dtos.UsuarioUpdateDTO;
import com.pinheiro.ebook.entidades.Usuario;
import com.pinheiro.ebook.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioDTO create(UsuarioCreateDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        Usuario saved = usuarioRepository.save(usuario);
        return toDTO(saved);
    }

    public UsuarioDTO update(Long id, UsuarioUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario not found"));
        if (dto.nome() != null) usuario.setNome(dto.nome());
        if (dto.email() != null) usuario.setEmail(dto.email());
        if (dto.senha() != null) usuario.setSenha(passwordEncoder.encode(dto.senha()));
        Usuario saved = usuarioRepository.save(usuario);
        return toDTO(saved);
    }

    public UsuarioDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario not found"));
        return toDTO(usuario);
    }

    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario not found");
        }
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> findByEmail(String email) {
        // Assuming we add a method in repository
        return usuarioRepository.findByEmail(email);
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataCriacao(),
                usuario.getUltimaAtualizacao()
        );
    }
}

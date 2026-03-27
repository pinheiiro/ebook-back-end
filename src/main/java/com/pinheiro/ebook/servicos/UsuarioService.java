package com.pinheiro.ebook.servicos;

import com.pinheiro.ebook.dtos.*;
import com.pinheiro.ebook.entidades.Perfis;
import com.pinheiro.ebook.entidades.Usuario;
import com.pinheiro.ebook.repositorios.PerfisRepository;
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
    private PerfisRepository perfisRepository;

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
        return usuarioRepository.findByEmail(email);
    }

    public UsuarioDTOComPerfis findByIdComPerfis(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        return toDTOComPerfis(usuario);
    }

    public void adicionarPerfil(Long usuarioId, Long perfilId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        Perfis perfis = perfisRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        if (!usuario.getPerfis().contains(perfis)) {
            usuario.getPerfis().add(perfis);
            usuarioRepository.save(usuario);
        }
    }

    public void removerPerfil(Long usuarioId, Long perfilId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        Perfis perfis = perfisRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        usuario.getPerfis().remove(perfis);
        usuarioRepository.save(usuario);
    }

    public List<PerfisDTO> obterPerfisDoUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        return usuario.getPerfis().stream()
                .map(perfis -> new PerfisDTO(perfis.getId(), perfis.getNome()))
                .collect(Collectors.toList());
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

    private UsuarioDTOComPerfis toDTOComPerfis(Usuario usuario) {
        List<PerfisDTO> perfisDTO = usuario.getPerfis().stream()
                .map(perfis -> new PerfisDTO(perfis.getId(), perfis.getNome()))
                .collect(Collectors.toList());

        return new UsuarioDTOComPerfis(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataCriacao(),
                usuario.getUltimaAtualizacao(),
                perfisDTO
        );
    }
}

package com.pinheiro.ebook.controladores;

import com.pinheiro.ebook.dtos.PerfisDTO;
import com.pinheiro.ebook.dtos.UsuarioDTOComPerfis;
import com.pinheiro.ebook.servicos.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioPerfisController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}/com-perfis")
    public ResponseEntity<UsuarioDTOComPerfis> obterUsuarioComPerfis(@PathVariable Long id) {
        try {
            UsuarioDTOComPerfis usuario = usuarioService.findByIdComPerfis(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}/perfis")
    public ResponseEntity<List<PerfisDTO>> obterPerfisDoUsuario(@PathVariable Long id) {
        try {
            List<PerfisDTO> perfis = usuarioService.obterPerfisDoUsuario(id);
            return ResponseEntity.ok(perfis);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{usuarioId}/perfis/{perfilId}")
    public ResponseEntity<Void> adicionarPerfil(
            @PathVariable Long usuarioId,
            @PathVariable Long perfilId) {
        try {
            usuarioService.adicionarPerfil(usuarioId, perfilId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{usuarioId}/perfis/{perfilId}")
    public ResponseEntity<Void> removerPerfil(
            @PathVariable Long usuarioId,
            @PathVariable Long perfilId) {
        try {
            usuarioService.removerPerfil(usuarioId, perfilId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}


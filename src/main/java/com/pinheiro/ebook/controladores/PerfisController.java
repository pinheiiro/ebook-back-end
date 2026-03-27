package com.pinheiro.ebook.controladores;

import com.pinheiro.ebook.dtos.PerfisCreateDTO;
import com.pinheiro.ebook.dtos.PerfisDTO;
import com.pinheiro.ebook.dtos.PerfisUpdateDTO;
import com.pinheiro.ebook.servicos.PerfisService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfis")
public class PerfisController {

    @Autowired
    private PerfisService perfisService;

    @PostMapping
    public ResponseEntity<PerfisDTO> criar(@Valid @RequestBody PerfisCreateDTO dto) {
        try {
            PerfisDTO perfis = perfisService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(perfis);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfisDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PerfisUpdateDTO dto) {
        try {
            PerfisDTO perfis = perfisService.update(id, dto);
            return ResponseEntity.ok(perfis);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfisDTO> obterPorId(@PathVariable Long id) {
        try {
            PerfisDTO perfis = perfisService.findById(id);
            return ResponseEntity.ok(perfis);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PerfisDTO>> obterTodos() {
        List<PerfisDTO> perfis = perfisService.findAll();
        return ResponseEntity.ok(perfis);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            perfisService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<PerfisDTO> obterPorNome(@PathVariable String nome) {
        try {
            PerfisDTO perfis = perfisService.findByNome(nome);
            return ResponseEntity.ok(perfis);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}


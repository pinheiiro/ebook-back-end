package com.pinheiro.ebook.controladores;

import com.pinheiro.ebook.dtos.EbookCreateDTO;
import com.pinheiro.ebook.dtos.EbookDTO;
import com.pinheiro.ebook.dtos.EbookUpdateDTO;
import com.pinheiro.ebook.servicos.EbookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ebooks")
public class EbookController {

    @Autowired
    private EbookService ebookService;

    @PostMapping
    public ResponseEntity<EbookDTO> create(@Valid @RequestBody EbookCreateDTO dto) {
        EbookDTO ebook = ebookService.create(dto);
        return ResponseEntity.ok(ebook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EbookDTO> update(@PathVariable Long id, @Valid @RequestBody EbookUpdateDTO dto) {
        EbookDTO ebook = ebookService.update(id, dto);
        return ResponseEntity.ok(ebook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EbookDTO> findById(@PathVariable Long id) {
        EbookDTO ebook = ebookService.findById(id);
        return ResponseEntity.ok(ebook);
    }

    @GetMapping
    public ResponseEntity<List<EbookDTO>> findAll() {
        List<EbookDTO> ebooks = ebookService.findAll();
        return ResponseEntity.ok(ebooks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ebookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

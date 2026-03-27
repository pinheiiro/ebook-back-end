package com.pinheiro.ebook.controladores;

import com.pinheiro.ebook.dtos.AutenticacaoDTO;
import com.pinheiro.ebook.dtos.LoginComTokenDTO;
import com.pinheiro.ebook.dtos.UsuarioCreateDTO;
import com.pinheiro.ebook.servicos.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginComTokenDTO> login(@RequestBody @Valid AutenticacaoDTO dto) {
        return ResponseEntity.ok(loginService.login(dto));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<LoginComTokenDTO> cadastro(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loginService.registro(usuarioCreateDTO));
    }
}

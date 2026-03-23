package com.pinheiro.ebook.servicos;

import com.pinheiro.ebook.dtos.AutenticacaoDTO;
import com.pinheiro.ebook.dtos.LoginDTO;
import com.pinheiro.ebook.dtos.UsuarioCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TokenService tokenService;
    public LoginDTO login(AutenticacaoDTO dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.senha()));
        var usuario = usuarioService.findByEmail(dto.email());
        var token = tokenService.gerarToken(usuario.get());
        return new LoginDTO(token);
    }

    public LoginDTO refresh(String token) {
        var subject = tokenService.validarToken(token);
        var usuario = usuarioService.findByEmail(subject);
        var novoToken = tokenService.gerarToken(usuario.get());
        return new LoginDTO(novoToken);
    }

    public LoginDTO registro(UsuarioCreateDTO dto) {
        usuarioService.create(dto);
        LoginDTO token = this.login(new AutenticacaoDTO(dto.email(), dto.senha()));
        return token;
    }
}

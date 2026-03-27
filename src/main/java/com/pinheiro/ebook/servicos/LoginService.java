package com.pinheiro.ebook.servicos;

import com.pinheiro.ebook.dtos.*;
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

    @Autowired
    private RefreshTokenService refreshTokenService;

    public LoginComTokenDTO login(AutenticacaoDTO dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.senha()));
        var usuario = usuarioService.findByEmail(dto.email());
        var token = tokenService.gerarToken(usuario.get());
        var refreshToken = refreshTokenService.criarRefreshToken(usuario.get().getId());
        var usuarioDTOComPerfis = usuarioService.findByIdComPerfis(usuario.get().getId());

        return new LoginComTokenDTO(token, refreshToken.token(), usuarioDTOComPerfis);
    }

    public LoginComTokenDTO refresh(String token) {
        var subject = tokenService.validarToken(token);
        var usuario = usuarioService.findByEmail(subject);
        var novoToken = tokenService.gerarToken(usuario.get());
        var usuarioDTOComPerfis = usuarioService.findByIdComPerfis(usuario.get().getId());

        return new LoginComTokenDTO(novoToken, token, usuarioDTOComPerfis);
    }

    public LoginComTokenDTO registro(UsuarioCreateDTO dto) {
        usuarioService.create(dto);
        LoginComTokenDTO loginComToken = this.login(new AutenticacaoDTO(dto.email(), dto.senha()));
        return loginComToken;
    }
}

package com.sardeiro.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sardeiro.login.dto.AuthenticationRecord;
import com.sardeiro.login.dto.LoginResponseDTO;
import com.sardeiro.login.model.Usuario;
import com.sardeiro.login.repository.UsuarioRepository;
import com.sardeiro.login.seguranca.TokenService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated AuthenticationRecord data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email().toUpperCase(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var usuario = (Usuario) auth.getPrincipal();
        var token = tokenService.generateToken(usuario);
        return ResponseEntity.ok(new LoginResponseDTO(token, usuario));
    }

    @GetMapping("/login/google")
    public ResponseEntity<?> googleLogin(HttpServletRequest request) {

        OAuth2User oAuthUser = (OAuth2User) request.getUserPrincipal();
        
        if (oAuthUser == null) {
            return ResponseEntity.badRequest().body("Usuário não autenticado pelo Google");
        }

        String email = oAuthUser.getAttribute("email");

        UserDetails usuarioDetails = usuarioRepository.findByEmail(email);
        Usuario usuario = null;
        if (usuarioDetails == null) {
            usuario = new Usuario();
            usuario.setEmail(email);
            usuarioRepository.save(usuario);
        }

        String token = tokenService.generateToken(usuario);

        return ResponseEntity.ok(new LoginResponseDTO(token, usuario));
    }



}

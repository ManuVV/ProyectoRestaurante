package com.restaurante.backend.controller;

import com.restaurante.backend.model.UsuarioEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class TestController {

    @GetMapping("/user")
    public ResponseEntity<String> soloUsuario(@AuthenticationPrincipal UsuarioEntity usuario) {
        return ResponseEntity.ok("Hola " + usuario.getNombre() + ", eres un usuario normal 👤");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> soloAdmin(@AuthenticationPrincipal UsuarioEntity usuario) {
        return ResponseEntity.ok("Hola " + usuario.getNombre() + ", eres un administrador 👑");
    }
}
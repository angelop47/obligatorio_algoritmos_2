package com.example.obligatorio_arbol3.controllers;

import com.example.obligatorio_arbol3.models.Usuario;
import com.example.obligatorio_arbol3.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    @PostMapping("/{usuarioId}/familiar")
    public Usuario crearFamiliar(@PathVariable Long usuarioId, @RequestBody Usuario familiar, @RequestParam String parentesco) {
        return usuarioService.crearUsuarioComoFamiliar(usuarioId, familiar, parentesco);
    }

    @PostMapping("/{usuarioId}/hijo")
    public Usuario crearHijo(@PathVariable Long usuarioId, @RequestBody Usuario hijo) {
        return usuarioService.crearHijo(usuarioId, hijo);
    }

    @GetMapping("/{usuarioId}")
    public Usuario obtenerUsuarioPorId(@PathVariable Long usuarioId) {
        return usuarioService.obtenerUsuarioPorId(usuarioId);
    }

    // Cambiado para agregar un hijo a una madre
    @PutMapping("/{madreId}/agregar-hijo-a-madre/{hijoId}")
    public Usuario agregarHijoAMadre(@PathVariable Long madreId, @PathVariable Long hijoId) {
        return usuarioService.agregarHijoAMadre(madreId, hijoId);
    }

    // Cambiado para agregar un hijo a un padre
    @PutMapping("/{padreId}/agregar-hijo-a-padre/{hijoId}")
    public Usuario agregarHijoAPadre(@PathVariable Long padreId, @PathVariable Long hijoId) {
        return usuarioService.agregarHijoAPadre(padreId, hijoId);
    }

    @GetMapping("/{usuarioId}/familiares")
    public List<Usuario> obtenerFamiliares(@PathVariable Long usuarioId) {
        return usuarioService.obtenerFamiliares(usuarioId);
    }
}
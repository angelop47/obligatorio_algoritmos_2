package com.example.obligatorio_arbol3.services;

import com.example.obligatorio_arbol3.models.Usuario;
import com.example.obligatorio_arbol3.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear un usuario básico
    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Crear un usuario como familiar directo (padre o madre) de un usuario existente
    public Usuario crearUsuarioComoFamiliar(Long usuarioId, Usuario familiar, String parentesco) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Usuario nuevoFamiliar = usuarioRepository.save(familiar);

        if ("padre".equalsIgnoreCase(parentesco)) {
            usuario.setPadre(nuevoFamiliar);
        } else if ("madre".equalsIgnoreCase(parentesco)) {
            usuario.setMadre(nuevoFamiliar);
        } else {
            throw new IllegalArgumentException("Parentesco no válido");
        }

        return usuarioRepository.save(usuario);
    }

    // Crear un hijo nuevo y asociarlo automáticamente a un padre o madre basado en el género del usuario
    public Usuario crearHijo(Long usuarioId, Usuario hijo) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Usuario nuevoHijo = usuarioRepository.save(hijo);

        if ("Masculino".equalsIgnoreCase(usuario.getGenero())) {
            nuevoHijo.setPadre(usuario);
            usuario.getHijosDePadre().add(nuevoHijo);
        } else if ("Femenino".equalsIgnoreCase(usuario.getGenero())) {
            nuevoHijo.setMadre(usuario);
            usuario.getHijosDeMadre().add(nuevoHijo);
        } else {
            throw new IllegalArgumentException("Género no válido para definir la relación parental");
        }

        usuarioRepository.save(usuario);
        return nuevoHijo;
    }

    // Obtener un usuario por ID
    public Usuario obtenerUsuarioPorId(Long usuarioId) {
        return usuarioRepository.findById(usuarioId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    // Agregar un hijo existente a una madre
    public Usuario agregarHijoAMadre(Long madreId, Long hijoId) {
        Usuario madre = usuarioRepository.findById(madreId).orElseThrow(() -> new IllegalArgumentException("Madre no encontrada"));
        Usuario hijo = usuarioRepository.findById(hijoId).orElseThrow(() -> new IllegalArgumentException("Hijo no encontrado"));

        hijo.setMadre(madre);
        madre.getHijosDeMadre().add(hijo);

        usuarioRepository.save(hijo);
        usuarioRepository.save(madre);

        return hijo;
    }

    // Agregar un hijo existente a un padre
    public Usuario agregarHijoAPadre(Long padreId, Long hijoId) {
        Usuario padre = usuarioRepository.findById(padreId).orElseThrow(() -> new IllegalArgumentException("Padre no encontrado"));
        Usuario hijo = usuarioRepository.findById(hijoId).orElseThrow(() -> new IllegalArgumentException("Hijo no encontrado"));

        hijo.setPadre(padre);
        padre.getHijosDePadre().add(hijo);

        usuarioRepository.save(hijo);
        usuarioRepository.save(padre);

        return hijo;
    }

    // Obtener la lista de familiares (padre, madre e hijos) de un usuario
    public List<Usuario> obtenerFamiliares(Long usuarioId) {
        Usuario usuario = obtenerUsuarioPorId(usuarioId);
        List<Usuario> familiares = new ArrayList<>();

        if (usuario.getPadre() != null) familiares.add(usuario.getPadre());
        if (usuario.getMadre() != null) familiares.add(usuario.getMadre());
        familiares.addAll(usuario.getHijosDePadre());
        familiares.addAll(usuario.getHijosDeMadre());

        return familiares;
    }
}

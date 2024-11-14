package com.example.obligatorio_arbol3.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private Integer edad;
    private String genero; // Nuevo campo para el género

    @ManyToOne
    @JoinColumn(name = "padre_id")
    private Usuario padre;

    @ManyToOne
    @JoinColumn(name = "madre_id")
    private Usuario madre;

    @OneToMany(mappedBy = "padre")
    @JsonIgnore
    private List<Usuario> hijosDePadre = new ArrayList<>();

    @OneToMany(mappedBy = "madre")
    @JsonIgnore
    private List<Usuario> hijosDeMadre = new ArrayList<>();

    public Usuario() {}

    public Usuario(Long id, String nombre, String email, Integer edad, String genero, Usuario padre, Usuario madre, List<Usuario> hijosDePadre, List<Usuario> hijosDeMadre) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
        this.genero = genero;
        this.padre = padre;
        this.madre = madre;
        this.hijosDePadre = hijosDePadre;
        this.hijosDeMadre = hijosDeMadre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Usuario getPadre() {
        return padre;
    }

    public void setPadre(Usuario padre) {
        this.padre = padre;
    }

    public Usuario getMadre() {
        return madre;
    }

    public void setMadre(Usuario madre) {
        this.madre = madre;
    }

    public List<Usuario> getHijosDePadre() {
        return hijosDePadre;
    }

    public void setHijosDePadre(List<Usuario> hijosDePadre) {
        this.hijosDePadre = hijosDePadre;
    }

    public List<Usuario> getHijosDeMadre() {
        return hijosDeMadre;
    }

    public void setHijosDeMadre(List<Usuario> hijosDeMadre) {
        this.hijosDeMadre = hijosDeMadre;
    }
}
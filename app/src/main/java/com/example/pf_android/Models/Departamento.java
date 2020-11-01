package com.example.pf_android.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Departamento {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("nombre")
    @Expose
    private String nombre;

    public Departamento() {
    }

    public Departamento(Long id, String nombre) {
        super();
        this.id = id;
        this.nombre = nombre;
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

}
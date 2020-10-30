package com.example.pf_android.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Localidad {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("nombre")
    @Expose
    private String nombre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
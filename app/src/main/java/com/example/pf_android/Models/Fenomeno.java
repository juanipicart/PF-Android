package com.example.pf_android.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fenomeno {

    @SerializedName("codigo")
    @Expose
    private String codigo;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("nombreFen")
    @Expose
    private String nombreFen;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreFen() {
        return nombreFen;
    }

    public void setNombreFen(String nombreFen) {
        this.nombreFen = nombreFen;
    }

}